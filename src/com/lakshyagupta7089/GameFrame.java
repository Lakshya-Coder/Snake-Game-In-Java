package com.lakshyagupta7089;

import com.lakshyagupta7089.util.Constants;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameFrame extends JFrame implements ActionListener {

    JRadioButton slow, fast, medium;
    ImageIcon tick_icon;
    ImageIcon cross_icon;

    JLabel label = new JLabel("Chose the speed of the snake!");

    public GameFrame() {
        tick_icon = new ImageIcon(Constants.ImageIconsPath.TICK);
        cross_icon = new ImageIcon(Constants.ImageIconsPath.CROSS);

        label.setHorizontalAlignment(JRadioButton.CENTER);
        label.setVerticalAlignment(JRadioButton.CENTER);
        label.setFont(new Font("Ink Free", Font.BOLD, 20));
        label.setForeground(Color.green);

        setUpRadioButtons();

        this.getContentPane().setBackground(Color.BLACK);
        this.add(label, BorderLayout.NORTH);
        this.add(medium, BorderLayout.CENTER);
        this.add(fast, BorderLayout.WEST);
        this.add(slow, BorderLayout.EAST);

        this.setTitle("Snake Game");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setResizable(false);
        this.setSize(400, 200);
        this.setLocationRelativeTo(null);
    }

    private void setUpRadioButtons() {
        fast = new JRadioButton("Fast");
        medium = new JRadioButton("Medium");
        slow = new JRadioButton("Slow");

        setDefaultOptionsToRadioButton(fast);
        setDefaultOptionsToRadioButton(medium);
        setDefaultOptionsToRadioButton(slow);

        ButtonGroup buttonGroup = new ButtonGroup();

        buttonGroup.add(fast);
        buttonGroup.add(slow);
        buttonGroup.add(medium);
    }

    public void setDefaultOptionsToRadioButton(JRadioButton radioButton) {
        radioButton.setHorizontalAlignment(JRadioButton.CENTER);
        radioButton.setVerticalAlignment(JRadioButton.CENTER);
        radioButton.setFont(new Font("Ink Free", Font.BOLD, 20));
        radioButton.addActionListener(this);
        radioButton.setForeground(Color.cyan);
        radioButton.setBackground(Color.BLACK);
        radioButton.setIcon(cross_icon);
        radioButton.setSelectedIcon(tick_icon);
        radioButton.setFocusable(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String list[] = {"Yes", "No"};

        if (e.getSource() == fast) {
            int x = JOptionPane.showOptionDialog(null, "You choose high!", "High", JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.INFORMATION_MESSAGE, null, list, 0);

            if (x == 0) {
                this.dispose();
                new GamePanel("High", 50);
            }

        } else if (e.getSource() == medium) {
            int x = JOptionPane.showOptionDialog(null, "You choose medium!", "Medium", JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.INFORMATION_MESSAGE, null, list, 0);

            if (x == 0) {
                this.dispose();
                new GamePanel("Medium", 80);
            }

        } else if (e.getSource() == slow) {
            int x = JOptionPane.showOptionDialog(null, "You choose slow!", "Slow", JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.INFORMATION_MESSAGE, null, list, 0);

            if (x == 0) {
                this.dispose();
                new GamePanel("Slow", 150);
            }

        }
    }
}
