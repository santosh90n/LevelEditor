/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xyz.alexac.leveleditor.ui;

/**
 *
 * @author alex-ac
 */
public class RenameDialog extends javax.swing.JDialog {
  public interface OnSuccessAction {
    public void DoRename(String name);
  };

  private final OnSuccessAction action;

  /**
   * Creates new form RenameDialog
   */
  public RenameDialog(java.awt.Frame parent, boolean modal, String name,
                      OnSuccessAction action) {
    super(parent, modal);
    this.action = action;
    initComponents();
    nameField.setText(name);
  }

  /**
   * This method is called from within the constructor to initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is always
   * regenerated by the Form Editor.
   */
  @SuppressWarnings("unchecked")
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {
    java.awt.GridBagConstraints gridBagConstraints;

    renameLabel = new javax.swing.JLabel();
    nameField = new javax.swing.JTextField();
    cancelButton = new javax.swing.JButton();
    okButton = new javax.swing.JButton();
    filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 32767));

    setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
    getContentPane().setLayout(new java.awt.GridBagLayout());

    renameLabel.setText("Rename:");
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 0;
    gridBagConstraints.weighty = 0.1;
    getContentPane().add(renameLabel, gridBagConstraints);

    nameField.setText("name");
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 1;
    gridBagConstraints.gridy = 0;
    gridBagConstraints.gridwidth = 3;
    gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
    gridBagConstraints.weightx = 0.1;
    gridBagConstraints.weighty = 0.1;
    getContentPane().add(nameField, gridBagConstraints);

    cancelButton.setText("Cancel");
    cancelButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        cancel(evt);
      }
    });
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 2;
    gridBagConstraints.gridy = 1;
    getContentPane().add(cancelButton, gridBagConstraints);

    okButton.setText("OK");
    okButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        ok(evt);
      }
    });
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 3;
    gridBagConstraints.gridy = 1;
    getContentPane().add(okButton, gridBagConstraints);
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 1;
    gridBagConstraints.gridy = 1;
    gridBagConstraints.weightx = 0.1;
    getContentPane().add(filler1, gridBagConstraints);

    pack();
  }// </editor-fold>//GEN-END:initComponents

  private void cancel(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancel
    dispose();
  }//GEN-LAST:event_cancel

  private void ok(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ok
    action.DoRename(nameField.getText());
    dispose();
  }//GEN-LAST:event_ok

  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JButton cancelButton;
  private javax.swing.Box.Filler filler1;
  private javax.swing.JTextField nameField;
  private javax.swing.JButton okButton;
  private javax.swing.JLabel renameLabel;
  // End of variables declaration//GEN-END:variables
}
