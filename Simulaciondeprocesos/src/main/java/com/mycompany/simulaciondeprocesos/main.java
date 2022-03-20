/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.simulaciondeprocesos;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;

/**
 *
 */
public class main extends javax.swing.JFrame implements Runnable {

    Lista lista = new Lista();
    Nodo nodo = new Nodo();
    String hora, minutos, segundos, ampm;
    Calendar calendario;
    Thread h1;
    Pintores Dibujo = new Pintores();
    Robin RR = new Robin();
    int restar1 = 0;
    private int contadorP = 0;
    int[] Memoria;
    int limMem;
    int x = 150;
    int y = 700;


    public main() {
        initComponents();
        h1 = new Thread((Runnable) this);
        h1.start();
        // timer.start();
        RR.start();
        Dibujo.start();
        limMem=41;
        Memoria = new int[41];
        Arrays.fill(Memoria, -1);
    }

    public void Blanquear() {
        Graphics g = getGraphics();
        Graphics lapiz = getGraphics();
        //Panel Memoria
        g.setColor(Color.WHITE);
        g.fillRect(x, y-(14*limMem), 100, 14 * limMem);

        //Activador
        g.setColor(Color.RED);
        g.fillRect(x, y-(14*(limMem+1)), 100, 14);
        lapiz.setColor(Color.WHITE);
        lapiz.drawString("Activador", x + 5, y-(14*(limMem+1)) + 10);

        //SO
        g.setColor(Color.RED);
        g.fillRect(x, y, 100, 14);
        lapiz.setColor(Color.WHITE);
        lapiz.drawString("S.O.", x + 5, y + 10);

        //Etiqueta del    l Proceso
        lapiz.setColor(Color.BLACK);
        for (int i = 0; i <= limMem; i++) {
            lapiz.drawString(String.format("%02d", i)+" ---", x - 30, y - (14 * i)-5);
            lapiz.drawString("_", x - 10, y - (14 * i)-2);
        }

    }

    public void Repaint() {
        Blanquear();
        for (int i = 0; i < Memoria.length; i++) {
            if (Memoria[i] != -1) {
                int id = Memoria[Memoria[i]];
                Pintor("P" + id, Memoria[i] - i + 1, Memoria[i] + 1, false);
                i = Memoria[i];
            }
        }

    }

    public class Robin extends Thread {

        @Override
        public void run() {
            try {
                Nodo EnCurso = null;
                boolean First;
                while (true) {
                    PContador(limMem+1, false);
                    tabla("Activador", limMem, limMem, limMem);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    PContador(limMem+1, true);

                    if (lista.getTamanio() > 0) {
                        First = false;

                        if (EnCurso == null) {
                            EnCurso = lista.getInicio();
                            First = true;
                        }
                        tabla(EnCurso.getNombre(), EnCurso.getLimite() + EnCurso.getTiempo(), EnCurso.getBase(), EnCurso.getLimite());

                        EnCurso.setTiempo(EnCurso.getTiempo() - 1);

                        PContador(EnCurso.getLimite() + EnCurso.getTiempo() + 1, false);
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        PContador(EnCurso.getLimite() + EnCurso.getTiempo() + 1, true);

                        if (EnCurso.getTiempo() <= 0) {
                            if (First) {
                                lista.setInicio(EnCurso.getSiguiente());
                            } else {
                                Nodo Aux = EnCurso.getAnterior();
                                Aux.setSiguiente(EnCurso.getSiguiente());
                                if (EnCurso.getSiguiente() != null) {
                                    EnCurso.getSiguiente().setAnterior(Aux);
                                }
                            }
                            Delete(EnCurso.getLimite());
                            lista.CambiarTamanio();
                            historial += "P" + EnCurso.getId() + "  finalizado a las " + lblHora.getText() + "\n";
                            txthisto.setText(historial);
                            Pintor("", EnCurso.getBase() - EnCurso.getLimite() + 1, EnCurso.getBase() + 1, true);

                        }

                        EnCurso = EnCurso.getSiguiente();
                    }

                }
            } catch (Exception e) {
                System.out.println(e);

            }
        }

    }

    public class Pintores extends Thread {

        private boolean run = false;

        public void Running() {
            run = true;
        }

        @Override
        public void run() {
            while (true) {
                while (run) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    Repaint();
                    run = false;
                }
            }
        }
    }

    public void PContador(int valores, boolean Borrar) {
        Graphics g = getGraphics();

        if (Borrar) {
            g.setColor(Color.WHITE);
        } else {
            g.setColor(Color.GREEN);
        }
        g.fillRect(x + 105, y - (14 * valores), 20, 14);
    }


    public void Pintor(String p, int t, int base, boolean Borrar) {

        Graphics g = getGraphics();

        //Borrar Proceso
        if (Borrar) {
            g.setColor(Color.WHITE);
        } else {
            //Dibujar Proceso
            g.setColor(Color.BLUE);
        }
        g.fillRect(x, y - (14 * base), 100, 14 * t);
        g.setColor(Color.WHITE);
        g.drawRect(x, y - (14 * base), 100, 14 * t);

        //Etiqueta del Proceso
        Graphics lapiz = getGraphics();
        lapiz.setColor(Color.WHITE);
        lapiz.drawString(p, x + 30, y - (14 * base) + (7 * t + 5));

    }

    private void tabla(String nombrep, int puntero, int basep, int limitep) {
        lbl_namep.setText(nombrep);
        jLabel11.setText("" + (puntero));
        lbl_Base.setText(" " + (basep));
        lbl_Lim.setText(" " + limitep);
    }

    @Override
    public void run() {
        Thread currentTime = Thread.currentThread();
        while (currentTime == h1) {
            calcula();
            lblHora.setText(hora + ":" + minutos + ":" + segundos + " " + ampm);
            System.out.println("Iniciar hora");
            try {
                Thread.sleep(1000);

            } catch (InterruptedException ex) {
                Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void calcula() {
        Calendar calendario = new GregorianCalendar();
        Date fechaHoraActual = new Date();

        calendario.setTime(fechaHoraActual);
        ampm = calendario.get(Calendar.AM_PM) == Calendar.AM ? "AM" : "PM";
        if (ampm.equals("PM")) {
            int h = calendario.get(Calendar.HOUR_OF_DAY) - 12;
            hora = h > 9 ? "" + h : "0" + h;

        } else {
            hora = calendario.get(Calendar.HOUR_OF_DAY) > 9 ? "" + calendario.get(Calendar.HOUR_OF_DAY) : "0" + calendario.get(Calendar.HOUR_OF_DAY);
        }
        minutos = calendario.get(Calendar.MINUTE) > 9 ? "" + calendario.get(Calendar.MINUTE) : "0" + calendario.get(Calendar.MINUTE);
        segundos = calendario.get(Calendar.SECOND) > 9 ? "" + calendario.get(Calendar.SECOND) : "0" + calendario.get(Calendar.SECOND);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txthisto = new javax.swing.JTextArea();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        lbl_namep = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        lbl_Base = new javax.swing.JLabel();
        lbl_Lim = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jbtnAgregar = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        lblHora = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(51, 51, 0));
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowDeiconified(java.awt.event.WindowEvent evt) {
                formWindowDeiconified(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setText("Memoria principal");

        jLabel2.setFont(new java.awt.Font("Trebuchet MS", 1, 36)); // NOI18N
        jLabel2.setText("Procesador");

        jPanel1.setBackground(new java.awt.Color(204, 255, 255));

        txthisto.setEditable(false);
        txthisto.setColumns(20);
        txthisto.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txthisto.setLineWrap(true);
        txthisto.setRows(5);
        txthisto.setWrapStyleWord(true);
        jScrollPane2.setViewportView(txthisto);

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jLabel7.setText("Calendarizador");

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jLabel8.setText("Contador de programa");

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jLabel9.setText("Base:");

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jLabel10.setText("Limite:");

        lbl_namep.setFont(new java.awt.Font("Segoe UI Black", 1, 18)); // NOI18N
        lbl_namep.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_namep.setText("?");

        jLabel11.setFont(new java.awt.Font("Segoe UI Black", 1, 18)); // NOI18N
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("?");

        lbl_Base.setFont(new java.awt.Font("Segoe UI Black", 1, 16)); // NOI18N
        lbl_Base.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_Base.setText("?");

        lbl_Lim.setFont(new java.awt.Font("Segoe UI Black", 1, 16)); // NOI18N
        lbl_Lim.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_Lim.setText("?");

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jLabel12.setText("Historial");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(106, 106, 106)
                        .addComponent(jLabel7))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(55, 55, 55)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel10)
                                .addGap(45, 45, 45)
                                .addComponent(lbl_Lim, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(37, 37, 37)
                                        .addComponent(jLabel12))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(53, 53, 53)
                                        .addComponent(lbl_Base, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)))))))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(101, 101, 101)
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(41, 41, 41)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(79, 79, 79)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbl_namep, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8))))
                .addContainerGap(86, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7)
                .addGap(18, 18, 18)
                .addComponent(lbl_namep)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel11)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(lbl_Base, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_Lim)
                    .addComponent(jLabel10))
                .addGap(34, 34, 34)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 199, Short.MAX_VALUE)
                .addContainerGap())
        );

        jbtnAgregar.setBackground(new java.awt.Color(0, 204, 204));
        jbtnAgregar.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jbtnAgregar.setForeground(new java.awt.Color(255, 255, 255));
        jbtnAgregar.setText("Agregar  Proceso");
        jbtnAgregar.setToolTipText("");
        jbtnAgregar.setActionCommand("Agregar Proceso");
        jbtnAgregar.setBorderPainted(false);
        jbtnAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnAgregarActionPerformed(evt);
            }
        });

        jLabel4.setText("0x00FFh");

        jLabel5.setText("0x0000h");

        lblHora.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        lblHora.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Hora del sistema");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(84, 84, 84)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(53, 53, 53)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel4))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 251, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(131, 131, 131))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(280, 280, 280))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(lblHora, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(234, 234, 234))))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jbtnAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(35, Short.MAX_VALUE)
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(23, 23, 23))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(231, 231, 231)
                        .addComponent(jbtnAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblHora, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(22, 22, 22))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public int Disponible(int tam, int id) {
        try {
            int Inicio = 0;
            boolean Start = true;
            int Suficiente = tam;
            int Base = -1;
            for (int i = 0; i < Memoria.length; i++) {
                if (Suficiente > 0) {
                    if (Memoria[i] == -1 && Start) {
                        Inicio = i;
                        Start = false;
                        Suficiente--;
                    } else if (Memoria[i] == -1 && !Start) {
                        Suficiente--;
                    } else {
                        i = Memoria[i];
                        Start = true;
                        Suficiente = tam;
                    }
                } else {
                    i = Memoria.length;
                }
            }
            if (Suficiente == 0) {
                Memoria[Inicio] = Inicio + tam - 1;
                Memoria[Inicio + tam - 1] = id;
                Base = Inicio + tam - 1;

                return Base;
            } else {
                return Base;
            }
        } catch (Exception e) {
            return -1;
        }
    }

    public void Delete(int Lim) {
        int Base = Memoria[Lim];
        Memoria[Base] = -1;
        Memoria[Lim] = -1;
    }

    String historial = "";
    private void jbtnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnAgregarActionPerformed
        int tiempo = (int) (Math.random() * 9 + 1);
        int Base = Disponible(tiempo, lista.getId_next());
        if (Base >= 0) {
            Pintor("P" + lista.getId_next(), tiempo, Base + 1, false);
            lista.agregarAlFinal(lista.getId_next(), "P " + lista.getId_next(), Base, tiempo);
            historial += "P" + this.contadorP + "  creado a las " + lblHora.getText() +"\n";
            txthisto.setText(historial);
            contadorP++;
            jbtnAgregar.enable(false);
                        try {
                Thread.sleep(100);

            } catch (InterruptedException ex) {
                Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
            }
                                    jbtnAgregar.enable(true);

        } else {
            JOptionPane.showMessageDialog(null, "No queda espacio espera a que los procesos sean terminados");
        }
    }//GEN-LAST:event_jbtnAgregarActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        Blanquear();

        // TODO add your handling code here:
    }//GEN-LAST:event_formWindowOpened

    private void formWindowDeiconified(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowDeiconified
        // TODO add your handling code here:
        Dibujo.Running();
    }//GEN-LAST:event_formWindowDeiconified

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new main().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton jbtnAgregar;
    private javax.swing.JLabel lblHora;
    private javax.swing.JLabel lbl_Base;
    private javax.swing.JLabel lbl_Lim;
    private javax.swing.JLabel lbl_namep;
    private javax.swing.JTextArea txthisto;
    // End of variables declaration//GEN-END:variables
}
