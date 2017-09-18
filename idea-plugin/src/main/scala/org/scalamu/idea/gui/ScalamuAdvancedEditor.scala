package org.scalamu.idea.gui

import javax.swing.JComponent

import com.intellij.openapi.options.SettingsEditor
import com.intellij.openapi.project.Project
import org.scalamu.idea.configuration.ScalamuRunConfiguration

class ScalamuAdvancedEditor(val project: Project) extends SettingsEditor[ScalamuRunConfiguration] {
  private[this] val form = new ScalamuAdvancedConfigurationForm

  override def createEditor(): JComponent                                    = form.getMainPanel
  override def applyEditorTo(configuration: ScalamuRunConfiguration): Unit   = configuration(form)
  override def resetEditorFrom(configuration: ScalamuRunConfiguration): Unit = form(configuration)
}
