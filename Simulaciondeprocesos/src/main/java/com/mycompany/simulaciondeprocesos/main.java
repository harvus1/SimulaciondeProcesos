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
    DefaultTableModel modelo;
    String hora, minutos, segundos, ampm;
    Calendar calendario;
    Thread h1;
    Robin RR = new Robin();
    int restar1 = 0;
    int tamañomemoria = 100;
    private int contadorP = 0;
    int[] Memoria;

//    int tamañoi=0;
//    int tamañof=0;
//    int tiempov=0;
//    String nombrev="";
    public main() {
        initComponents();
        h1 = new Thread((Runnable) this);
        h1.start();
        // timer.start();
        modelo = new DefaultTableModel();
        modelo.addColumn("Proceso");
        modelo.addColumn("Tiempo");
        RR.start();
        this.jTable1.setModel(modelo);
        Memoria = new int[40];
        Arrays.fill(Memoria, -1);
    }

    public void Blanquear() {
        Graphics g = getGraphics();
        Graphics lapiz = getGraphics();

        //Panel Memoria
        g.setColor(Color.WHITE);
        g.fillRect(x, 90, x + 80, 14 * 40);

        //Activador
        g.setColor(Color.RED);
        g.fillRect(x, 76, x + 80, 14);
        lapiz.setColor(Color.WHITE);
        lapiz.drawString("Activador", x + 5, 76 + 10);

        //SO
        g.setColor(Color.RED);
        g.fillRect(x, y, x + 50, 14);
        lapiz.setColor(Color.WHITE);
        lapiz.drawString("S.O.", x + 5, y + 10);

        //Etiqueta del    l Proceso
        lapiz.setColor(Color.BLACK);
        for (int i = 0; i <= 40; i++) {
            lapiz.drawString("--", x - 15, y - (14 * i));
        }

    }

    public class Robin extends Thread {

        @Override
        public void run() {
            try {
                Nodo EnCurso = null;
                boolean First;
                int nice = 1;
                while (true) {
                    modelo.getDataVector().removeAllElements();
                    PContador(41, false);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    PContador(41, true);

                    if (lista.getTamanio() > 0) {
                        First = false;

                        if (EnCurso == null) {
                            EnCurso = lista.getInicio();
                            First = true;
                        }
                        String[] nombre = new String[10000];
                        int[] tiempo = new int[10000];
                        Object[] unidor = new Object[10000];
                        nombre = lista.Listarb();
                        tiempo = lista.Listarc();

                        for (int i = 0; i <= lista.getTamanio() - 1; i = i + 1) {
                            unidor[0] = nombre[i];
                            unidor[1] = tiempo[i];
                            modelo.addRow(unidor);
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

    public void PContador(int valores, boolean Borrar) {
        Graphics g = getGraphics();

        if (Borrar) {
            g.setColor(Color.WHITE);
        } else {
            g.setColor(Color.GREEN);
        }
        g.fillRect(x + 105, y - (14 * valores), 20, 14);
    }

    int x = 50;
    int y = 650;
    int pos = 0;

    public void Pintor(String p, int t, int base, boolean Borrar) {

        Graphics g = getGraphics();

        //Borrar Proceso
        if (Borrar) {
            g.setColor(Color.WHITE);
        } else {
            //Dibujar Proceso
            g.setColor(Color.BLUE);
        }
        g.fillRect(x, y - (14 * base), x + 50, 14 * t);
        g.setColor(Color.WHITE);
        g.drawRect(x, y - (14 * base), x + 50, 14 * t);

        //Etiqueta del Proceso
        Graphics lapiz = getGraphics();
        lapiz.setColor(Color.WHITE);
        lapiz.drawString(p, x + 30, y - (14 * base) + (7 * t + 5));

    }

    private void tabla(String nombrep, int puntero, int basep, int limitep) {
        lbl_namep.setText(nombrep);
        jLabel11.setText("" + (puntero - 1));
        lbl_Base.setText(" " + basep);
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
        jTextField1 = new javax.swing.JTextField();
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
        jButton1 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        lblHora = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(51, 51, 0));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jLabel1.setText("Memoria principal");

        jLabel2.setText("Procesador");

        jPanel1.setBackground(new java.awt.Color(204, 255, 255));

        jTextField1.setText("Historial");
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        txthisto.setEditable(false);
        txthisto.setColumns(20);
        txthisto.setLineWrap(true);
        txthisto.setRows(5);
        txthisto.setWrapStyleWord(true);
        jScrollPane2.setViewportView(txthisto);

        jLabel7.setText("Calendarizador");

        jLabel8.setText("Contador de programa");

        jLabel9.setText("Base:");

        jLabel10.setText("Limite:");

        lbl_namep.setFont(new java.awt.Font("Segoe UI Black", 1, 18)); // NOI18N
        lbl_namep.setText("?");

        jLabel11.setFont(new java.awt.Font("Segoe UI Black", 1, 18)); // NOI18N
        jLabel11.setText("?");

        lbl_Base.setFont(new java.awt.Font("Segoe UI Black", 1, 14)); // NOI18N
        lbl_Base.setText("?");

        lbl_Lim.setFont(new java.awt.Font("Segoe UI Black", 1, 12)); // NOI18N
        lbl_Lim.setText("?");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addGap(52, 52, 52)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lbl_namep, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel7))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(26, 26, 26)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(3, 3, 3))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel9)
                            .addComponent(jLabel10))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(109, 109, 109))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(lbl_Base, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lbl_Lim, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addGap(26, 26, 26))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(jLabel7)
                .addGap(18, 18, 18)
                .addComponent(lbl_namep)
                .addGap(29, 29, 29)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel11)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(49, 49, 49)
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbl_Base)
                        .addGap(14, 14, 14)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(lbl_Lim))
                .addGap(34, 34, 34)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 117, Short.MAX_VALUE)
                .addGap(28, 28, 28))
        );

        jButton1.setText("Agregar proceso");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel4.setText("0x00FFh");

        jLabel5.setText("0x0000h");

        jPanel3.setBackground(new java.awt.Color(255, 51, 51));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 173, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 107, Short.MAX_VALUE)
        );

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "proceso", "Tiempo"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jLabel3.setText("Hora del sistema");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(520, 520, 520)
                .addComponent(jLabel3)
                .addGap(18, 18, 18)
                .addComponent(lblHora, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(322, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(77, 77, 77)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(34, 34, 34)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(34, 34, 34))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(155, 155, 155))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(123, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addGap(217, 217, 217)
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 369, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jLabel4)
                                    .addGap(86, 86, 86)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblHora, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(22, 22, 22))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public int Disponible(int tam) {
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
            Memoria[Inicio + tam - 1] = Inicio;
            Base = Inicio + tam - 1;

            return Base;
        } else {
            return Base;
        }
    }

    public void Delete(int Lim) {
        int Base = Memoria[Lim];
        Memoria[Base] = -1;
        Memoria[Lim] = -1;
    }

    String historial = "";
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        //Graficar();
        int a = 0;
        //  a = lista.Tamañolimite();
        int tiempo = (int) (Math.random() * 9 + 1);
        int Base = Disponible(tiempo);
        if (Base >= 0) {
            Pintor("P" + lista.getId_next(), tiempo, Base + 1, false);
            lista.agregarAlFinal(lista.getId_next(), "P " + lista.getId_next(), Base, tiempo);
            historial += "P" + this.contadorP + "  creado a las " + lblHora.getText() + "\n";
            txthisto.setText(historial);
            contadorP++;

        } else {
            JOptionPane.showMessageDialog(null, "No queda espacio espera a que los procesos sean terminados");

        }
        pos = pos + tiempo;
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        Blanquear();

        // TODO add your handling code here:
    }//GEN-LAST:event_formWindowOpened

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
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JLabel lblHora;
    private javax.swing.JLabel lbl_Base;
    private javax.swing.JLabel lbl_Lim;
    private javax.swing.JLabel lbl_namep;
    private javax.swing.JTextArea txthisto;
    // End of variables declaration//GEN-END:variables
}
