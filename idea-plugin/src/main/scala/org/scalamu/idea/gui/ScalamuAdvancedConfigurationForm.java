package org.scalamu.idea.gui;

import com.intellij.execution.configuration.EnvironmentVariablesComponent;
import com.intellij.openapi.ui.Messages;
import com.intellij.ui.RawCommandLineEditor;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import org.apache.commons.lang3.math.NumberUtils;
import org.scalamu.idea.configuration.ScalamuRunConfiguration;
import scala.Option;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class ScalamuAdvancedConfigurationForm {
  private EnvironmentVariablesComponent envVariables;
  private JFormattedTextField timeoutFactor;
  private JFormattedTextField timeoutConst;
  private JCheckBox enableVerboseLoggingCheckBox;
  private RawCommandLineEditor scalacOptions;
  private JPanel mainPanel;

  public ScalamuAdvancedConfigurationForm() {
    setupTimeouts();
  }

  public void apply(ScalamuRunConfiguration configuration) {
    envVariables.setEnvs(configuration.envVariables());
    timeoutConst.setText(Integer.toString(configuration.timeoutConst()));
    timeoutFactor.setText(Double.toString(configuration.timeoutFactor()));
    enableVerboseLoggingCheckBox.setSelected(configuration.verboseLogging());

    Option<String> stringOption = configuration.scalacParameters();
    if (stringOption.isDefined()) {
      scalacOptions.setText(stringOption.get());
    }
  }

  private void setupTimeouts() {
    timeoutConst.setText("2000");
    timeoutConst.setInputVerifier(new InputVerifier() {
      @Override
      public boolean verify(JComponent input) {
        JTextField tf = (JTextField) input;
        String text = tf.getText();
        boolean isNumber = NumberUtils.isParsable(text);
        return isNumber && Long.parseLong(text) >= 0;
      }

      @Override
      public boolean shouldYieldFocus(JComponent input) {
        boolean isValid = verify(input);
        if (!isValid) Messages.showErrorDialog("Please, enter a valid number > 0.", "Invalid Input.");
        return isValid;
      }
    });

    timeoutFactor.setText("1.5");
    timeoutFactor.setInputVerifier(new InputVerifier() {
      @Override
      public boolean verify(JComponent input) {
        JTextField tf = (JTextField) input;
        String text = tf.getText();
        boolean isNumber = NumberUtils.isParsable(text);
        return isNumber && Double.parseDouble(text) >= 0;
      }

      @Override
      public boolean shouldYieldFocus(JComponent input) {
        boolean isValid = verify(input);
        if (!isValid) Messages.showErrorDialog("Please, enter a valid double > 0.", "Invalid Input.");
        return isValid;
      }
    });
  }

  public int getTimeoutConst() {
    return Integer.parseInt(timeoutConst.getText());
  }

  public double getTimeoutFactor() {
    return Double.parseDouble(timeoutFactor.getText());
  }

  public boolean getVerboseLogging() {
    return enableVerboseLoggingCheckBox.isSelected();
  }

  public String getScalacOptions() {
    return scalacOptions.getText();
  }

  public Map<String, String> getEnvVariables() {
    return envVariables.getEnvs();
  }


  public JComponent getMainPanel() {
    return mainPanel;
  }

  {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
    $$$setupUI$$$();
  }

  /**
   * Method generated by IntelliJ IDEA GUI Designer
   * >>> IMPORTANT!! <<<
   * DO NOT edit this method OR call it in your code!
   *
   * @noinspection ALL
   */
  private void $$$setupUI$$$() {
    mainPanel = new JPanel();
    mainPanel.setLayout(new GridLayoutManager(6, 2, new Insets(0, 0, 0, 0), -1, -1));
    final JLabel label1 = new JLabel();
    label1.setText("scalac options:");
    mainPanel.add(label1, new GridConstraints(0, 0, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    scalacOptions = new RawCommandLineEditor();
    mainPanel.add(scalacOptions, new GridConstraints(1, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    envVariables = new EnvironmentVariablesComponent();
    mainPanel.add(envVariables, new GridConstraints(2, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    final JLabel label2 = new JLabel();
    label2.setText("Timeout factor:");
    mainPanel.add(label2, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    timeoutFactor = new JFormattedTextField();
    mainPanel.add(timeoutFactor, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
    final JLabel label3 = new JLabel();
    label3.setText("Timeout const, ms:");
    mainPanel.add(label3, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    timeoutConst = new JFormattedTextField();
    mainPanel.add(timeoutConst, new GridConstraints(4, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
    enableVerboseLoggingCheckBox = new JCheckBox();
    enableVerboseLoggingCheckBox.setText("Enable verbose logging");
    mainPanel.add(enableVerboseLoggingCheckBox, new GridConstraints(5, 0, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
  }

  /**
   * @noinspection ALL
   */
  public JComponent $$$getRootComponent$$$() {
    return mainPanel;
  }
}
