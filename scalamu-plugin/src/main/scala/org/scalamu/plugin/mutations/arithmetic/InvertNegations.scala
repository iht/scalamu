package org.scalamu.plugin.mutations.arithmetic

import org.scalamu.plugin.mutations.NumericTypesSupport
import org.scalamu.plugin._

import scala.tools.nsc.Global

/**
 * Mutation, thar inverts negations of integer and floating point numbers.
 * e.g.
 * {{{
 * val a = -b
 * }}}
 * is mutated to
 * {{{
 * val a = b
 * }}}
 */
case object InvertNegations extends Mutation with NumericTypesSupport { self =>
  override def mutatingTransformer(
    global: Global,
    config: MutationConfig
  ): MutatingTransformer = new MutatingTransformer(config)(global) {
    import global._

    override protected def mutation: Mutation = self

    override protected def transformer: Transformer = new Transformer {
      override protected val mutate: PartialFunction[Tree, Tree] = {
        case tree @ q"${lit: Constant}" if lit.isNumeric && lit.doubleValue < 0 =>
          val value: Any = lit.value match {
            case v: Byte =>
              if (v == Byte.MinValue) Byte.MaxValue
              else -lit.byteValue
            case v: Short =>
              if (v == Short.MinValue) Short.MaxValue
              else -lit.shortValue
            case v: Int =>
              if (v == Int.MinValue) Int.MaxValue
              else -lit.intValue
            case v: Long =>
              if (v == Long.MinValue) Long.MaxValue
              else -lit.longValue
            case _: Float  => -lit.doubleValue
            case _: Double => -lit.floatValue
          }
          val mutationResult = Literal(Constant(value))
          mutationResult.setType(tree.tpe.deconst)
          reportMutation(tree, mutationResult)
          guard(mutationResult, tree)
        case tree @ q"-${TreeWithType(term, tpe)}" if supportedTypes.exists(_ =:= tpe) =>
          val mutatedTerm    = super.transform(term)
          val mutationResult = q"$mutatedTerm"
          reportMutation(tree, mutationResult)
          guard(mutationResult, tree)
      }
    }
  }
}
