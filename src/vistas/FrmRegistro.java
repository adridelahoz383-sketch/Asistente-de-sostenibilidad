package vistas;

import controlador.ControladorPersistencia;
import java.util.List;
import modelo.Usuario;

/**
 *
 * @author pedro
 */
public class FrmRegistro extends javax.swing.JFrame {

    /**
     * Creates new form FrmRegistro
     */
    public FrmRegistro() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlHeader = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        pnlContenedor = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        pnlTarjeta16 = new javax.swing.JPanel();
        lblTitulo16 = new javax.swing.JLabel();
        lblSubtitulo16 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        btnRegistrar = new javax.swing.JButton();
        btnVolver = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        boxRol = new javax.swing.JComboBox<>();
        txtContrasena = new javax.swing.JPasswordField();
        txtCorreo = new javax.swing.JTextField();
        txtId = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("ecoComunidad - Registro de Usuario");
        setResizable(false);

        pnlHeader.setBackground(new java.awt.Color(0, 102, 102));
        pnlHeader.setPreferredSize(new java.awt.Dimension(400, 80));
        pnlHeader.setLayout(new java.awt.BorderLayout());

        jLabel5.setFont(new java.awt.Font("Segoe UI", 3, 24)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("BIENVENIDO A ECOCOMUNIDAD");
        pnlHeader.add(jLabel5, java.awt.BorderLayout.CENTER);

        getContentPane().add(pnlHeader, java.awt.BorderLayout.PAGE_START);

        pnlContenedor.setBackground(new java.awt.Color(255, 255, 255));
        pnlContenedor.setLayout(new java.awt.BorderLayout());

        jLabel1.setOpaque(true);
        jLabel1.setPreferredSize(new java.awt.Dimension(150, 37));
        pnlContenedor.add(jLabel1, java.awt.BorderLayout.LINE_END);

        jLabel2.setText("    ");
        jLabel2.setOpaque(true);
        jLabel2.setPreferredSize(new java.awt.Dimension(12, 50));
        pnlContenedor.add(jLabel2, java.awt.BorderLayout.PAGE_START);

        jLabel3.setOpaque(true);
        jLabel3.setPreferredSize(new java.awt.Dimension(150, 37));
        pnlContenedor.add(jLabel3, java.awt.BorderLayout.LINE_START);

        jLabel4.setText("   ");
        jLabel4.setOpaque(true);
        jLabel4.setPreferredSize(new java.awt.Dimension(9, 50));
        pnlContenedor.add(jLabel4, java.awt.BorderLayout.PAGE_END);

        pnlTarjeta16.setBackground(new java.awt.Color(255, 255, 255));
        pnlTarjeta16.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        lblTitulo16.setFont(new java.awt.Font("Segoe UI", 3, 22)); // NOI18N
        lblTitulo16.setText("Registrate");

        lblSubtitulo16.setBackground(new java.awt.Color(204, 204, 204));
        lblSubtitulo16.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
        lblSubtitulo16.setForeground(new java.awt.Color(153, 153, 153));
        lblSubtitulo16.setText("Para continuar");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        jLabel9.setText("Nombre");

        jLabel10.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        jLabel10.setText("Id");

        btnRegistrar.setBackground(new java.awt.Color(0, 102, 102));
        btnRegistrar.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        btnRegistrar.setForeground(new java.awt.Color(255, 255, 255));
        btnRegistrar.setText("Registrar");
        btnRegistrar.addActionListener(this::btnRegistrarActionPerformed);

        btnVolver.setBackground(new java.awt.Color(0, 102, 102));
        btnVolver.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        btnVolver.setForeground(new java.awt.Color(255, 255, 255));
        btnVolver.setText("Volver");
        btnVolver.addActionListener(this::btnVolverActionPerformed);

        jLabel11.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        jLabel11.setText("Correo");

        jLabel12.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        jLabel12.setText("Constrasena");

        jLabel13.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        jLabel13.setText("Rol");

        boxRol.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Ninguno", "Administrador", "Supervisor" }));

        javax.swing.GroupLayout pnlTarjeta16Layout = new javax.swing.GroupLayout(pnlTarjeta16);
        pnlTarjeta16.setLayout(pnlTarjeta16Layout);
        pnlTarjeta16Layout.setHorizontalGroup(
            pnlTarjeta16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTarjeta16Layout.createSequentialGroup()
                .addContainerGap(150, Short.MAX_VALUE)
                .addGroup(pnlTarjeta16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel11)
                    .addComponent(jLabel12)
                    .addComponent(lblSubtitulo16)
                    .addComponent(lblTitulo16)
                    .addComponent(jLabel9)
                    .addComponent(jLabel10)
                    .addComponent(jLabel13)
                    .addComponent(txtNombre)
                    .addComponent(boxRol, 0, 250, Short.MAX_VALUE)
                    .addComponent(txtCorreo)
                    .addComponent(txtContrasena)
                    .addComponent(btnRegistrar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnVolver, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtId))
                .addContainerGap(150, Short.MAX_VALUE))
        );
        pnlTarjeta16Layout.setVerticalGroup(
            pnlTarjeta16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTarjeta16Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(lblTitulo16)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblSubtitulo16)
                .addGap(42, 42, 42)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(boxRol, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCorreo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtContrasena, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
                .addComponent(btnRegistrar)
                .addGap(18, 18, 18)
                .addComponent(btnVolver)
                .addGap(36, 36, 36))
        );

        pnlContenedor.add(pnlTarjeta16, java.awt.BorderLayout.CENTER);

        getContentPane().add(pnlContenedor, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnRegistrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrarActionPerformed

        String nombre = txtNombre.getText().trim();
        String id = txtId.getText().trim();
        String correo = txtCorreo.getText().trim();

        String contrasena = new String(txtContrasena.getPassword()).trim();

        String rol = boxRol.getSelectedItem().toString();

        if (nombre.isEmpty() || id.isEmpty() || correo.isEmpty() || contrasena.isEmpty() || rol.equals("Ninguno")) {
            javax.swing.JOptionPane.showMessageDialog(this,
                    "Por favor, complete todos los campos y seleccione un rol válido.",
                    "Formulario Incompleto",
                    javax.swing.JOptionPane.WARNING_MESSAGE);
            return;
        }

        nombre = nombre.replace(";", "");
        id = id.replace(";", "");
        correo = correo.replace(";", "");
        contrasena = contrasena.replace(";", "");

        ControladorPersistencia persistencia = new ControladorPersistencia();
        List<Usuario> listaUsuarios = persistencia.cargarUsuarios();

        for (Usuario user : listaUsuarios) {
            if (user.getIdUsuario().equalsIgnoreCase(id)) {
                javax.swing.JOptionPane.showMessageDialog(this, "Error: Ya existe un usuario registrado con esa Identificación.", "ID Duplicado", javax.swing.JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (user.getCorreo().equalsIgnoreCase(correo)) {
                javax.swing.JOptionPane.showMessageDialog(this, "Error: Ya existe un usuario registrado con este Correo Electrónico.", "Correo Duplicado", javax.swing.JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        Usuario nuevoUsuario = new Usuario(id, nombre, correo, contrasena, rol);

        listaUsuarios.add(nuevoUsuario);

        persistencia.guardarUsuarios(listaUsuarios);

        javax.swing.JOptionPane.showMessageDialog(this,
                "¡Usuario registrado exitosamente!\nYa puede iniciar sesión.",
                "Registro Exitoso",
                javax.swing.JOptionPane.INFORMATION_MESSAGE);

        txtNombre.setText("");
        txtId.setText("");
        txtCorreo.setText("");
        txtContrasena.setText("");
        boxRol.setSelectedIndex(0);
    }//GEN-LAST:event_btnRegistrarActionPerformed

    private void btnVolverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVolverActionPerformed
        FrmLogin login = new FrmLogin();

        login.setVisible(true);

        this.dispose();
    }//GEN-LAST:event_btnVolverActionPerformed

    /**
     * @param args the command line arguments
     */
    /**
     * public static void main(String args[]) {
     *
     * try { for (javax.swing.UIManager.LookAndFeelInfo info :
     * javax.swing.UIManager.getInstalledLookAndFeels()) { if
     * ("Nimbus".equals(info.getName())) {
     * javax.swing.UIManager.setLookAndFeel(info.getClassName()); break; } } }
     * catch (ReflectiveOperationException |
     * javax.swing.UnsupportedLookAndFeelException ex) {
     * logger.log(java.util.logging.Level.SEVERE, null, ex); }
     *
     * java.awt.EventQueue.invokeLater(() -> new
     * FrmRegistro().setVisible(true)); }
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> boxRol;
    private javax.swing.JButton btnRegistrar;
    private javax.swing.JButton btnVolver;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel lblSubtitulo16;
    private javax.swing.JLabel lblTitulo16;
    private javax.swing.JPanel pnlContenedor;
    private javax.swing.JPanel pnlHeader;
    private javax.swing.JPanel pnlTarjeta16;
    private javax.swing.JPasswordField txtContrasena;
    private javax.swing.JTextField txtCorreo;
    private javax.swing.JTextField txtId;
    private javax.swing.JTextField txtNombre;
    // End of variables declaration//GEN-END:variables
}
