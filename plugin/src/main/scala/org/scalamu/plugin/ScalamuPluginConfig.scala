package org.scalamu.plugin

import org.scalamu.plugin.mutations.arithmetic._
import org.scalamu.plugin.mutations.controllflow._
import org.scalamu.plugin.mutations.methodcalls._
import org.scalamu.plugin.mutations.{ReplaceWithNil, ReplaceWithNone}

object ScalamuPluginConfig {
  val allMutations: Seq[Mutation] = Seq(
    ReplaceCaseWithWildcard,
    ReplaceMathOperators,
    ReplaceWithIdentityFunction,
    InvertNegations,
    AlwaysExecuteConditionals,
//    NeverExecuteConditionals,
    ReplaceConditionalBoundaries,
    NegateConditionals,
    RemoveUnitMethodCalls,
    ChangeRangeBoundary,
    ReplaceLogicalOperators,
    ReplaceWithNone,
    ReplaceWithNil
  )

  val mutationByName: Map[String, Mutation] =
    allMutations.map(m => m -> m.toString).toMap.map(_.swap)

  val mutationGuardPrefix: String = "org.scalamu.guards"
}
