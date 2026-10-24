package vistas;

import controlador.ControladorPersistencia;
import java.util.List;
import javax.swing.UnsupportedLookAndFeelException;
import modelo.Usuario;

public class FrmLogin extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(FrmLogin.class.getName());

    public FrmLogin() {
        initComponents();
        this.setLocationRelativeTo(null);
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
        txtCorreo = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        btnIniciarSesion = new javax.swing.JButton();
        lblRegistrar = new javax.swing.JLabel();
        txtContrasena = new javax.swing.JPasswordField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("ecoComunidad - Iniciar Sesión");
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
        lblTitulo16.setText("Iniciar Sesión");

        lblSubtitulo16.setBackground(new java.awt.Color(204, 204, 204));
        lblSubtitulo16.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
        lblSubtitulo16.setForeground(new java.awt.Color(153, 153, 153));
        lblSubtitulo16.setText("Para continuar");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        jLabel9.setText("Correo");

        jLabel10.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        jLabel10.setText("Contraseña");

        btnIniciarSesion.setBackground(new java.awt.Color(0, 102, 102));
        btnIniciarSesion.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        btnIniciarSesion.setForeground(new java.awt.Color(255, 255, 255));
        btnIniciarSesion.setText("Iniciar sesión");
        btnIniciarSesion.addActionListener(this::btnIniciarSesionActionPerformed);

        lblRegistrar.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        lblRegistrar.setForeground(new java.awt.Color(0, 102, 102));
        lblRegistrar.setText("¿No estás registrado? Registrate aqui.");
        lblRegistrar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblRegistrarMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout pnlTarjeta16Layout = new javax.swing.GroupLayout(pnlTarjeta16);
        pnlTarjeta16.setLayout(pnlTarjeta16Layout);
        pnlTarjeta16Layout.setHorizontalGroup(
            pnlTarjeta16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlTarjeta16Layout.createSequentialGroup()
                .addGap(0, 68, Short.MAX_VALUE)
                .addGroup(pnlTarjeta16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblSubtitulo16)
                    .addComponent(lblTitulo16)
                    .addComponent(jLabel9)
                    .addComponent(txtCorreo, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlTarjeta16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel10)
                        .addGroup(pnlTarjeta16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(btnIniciarSesion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblRegistrar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(txtContrasena))
                .addGap(70, 70, 70))
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
                .addComponent(txtCorreo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtContrasena, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24)
                .addComponent(btnIniciarSesion)
                .addGap(18, 18, 18)
                .addComponent(lblRegistrar)
                .addContainerGap(103, Short.MAX_VALUE))
        );

        pnlContenedor.add(pnlTarjeta16, java.awt.BorderLayout.CENTER);

        getContentPane().add(pnlContenedor, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnIniciarSesionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIniciarSesionActionPerformed

        String correoIngresado = txtCorreo.getText().trim();

        String contrasenaIngresada = new String(txtContrasena.getPassword());

        if (correoIngresado.isEmpty() || contrasenaIngresada.isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(this,
                    "Por favor, diligencie todos los campos obligatorios.",
                    "Campos Vacíos",
                    javax.swing.JOptionPane.WARNING_MESSAGE);
            return;
        }

        ControladorPersistencia persistencia = new ControladorPersistencia();

        List<Usuario> usuariosRegistrados = persistencia.cargarUsuarios();

        boolean autenticado = false;
        Usuario usuarioLogueado = null;

        for (Usuario user : usuariosRegistrados) {
            if (user.getCorreo().equalsIgnoreCase(correoIngresado) && user.getContrasena().equals(contrasenaIngresada)) {
                autenticado = true;
                usuarioLogueado = user;
                break;
            }
        }

        if (autenticado) {
            javax.swing.JOptionPane.showMessageDialog(this,
                    "¡Bienvenido al sistema, " + usuarioLogueado.getNombre() + "!\nRol: " + usuarioLogueado.getRol(),
                    "Inicio de Sesión Exitoso",
                    javax.swing.JOptionPane.INFORMATION_MESSAGE);

            FrmVentanaPrincipal principal = new FrmVentanaPrincipal(usuarioLogueado);
            principal.setLocationRelativeTo(null);
            principal.setVisible(true);
            this.dispose();

        } else {
            javax.swing.JOptionPane.showMessageDialog(this,
                    "Correo electrónico o contraseña incorrectos.\nIntente nuevamente.",
                    "Error de Autenticación",
                    javax.swing.JOptionPane.ERROR_MESSAGE);

            txtContrasena.setText("");
            txtContrasena.requestFocus();
        }

    }//GEN-LAST:event_btnIniciarSesionActionPerformed

    private void lblRegistrarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblRegistrarMouseClicked

        FrmRegistro ventanaRegistro = new FrmRegistro();

        ventanaRegistro.setLocationRelativeTo(null);

        ventanaRegistro.setVisible(true);

        this.dispose();
    }//GEN-LAST:event_lblRegistrarMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        
        try {
            
            javax.swing.UIManager.setLookAndFeel(new com.formdev.flatlaf.FlatLightLaf());
        } catch (UnsupportedLookAndFeelException ex) {
            System.err.println("Error al inicializar FlatLaf");
        }

        
        java.awt.EventQueue.invokeLater(() -> {
            new vistas.FrmLogin().setVisible(true); 
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnIniciarSesion;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel lblRegistrar;
    private javax.swing.JLabel lblSubtitulo16;
    private javax.swing.JLabel lblTitulo16;
    private javax.swing.JPanel pnlContenedor;
    private javax.swing.JPanel pnlHeader;
    private javax.swing.JPanel pnlTarjeta16;
    private javax.swing.JPasswordField txtContrasena;
    private javax.swing.JTextField txtCorreo;
    // End of variables declaration//GEN-END:variables
}
