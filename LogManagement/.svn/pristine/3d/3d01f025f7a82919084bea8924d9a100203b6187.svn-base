package ubpartner.logmanagement.swing;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.JTextArea;
import ubpartner.logmanagement.LMConfiguration;
import ubpartner.logmanagement.LogManagement;
import ubpartner.logmanagement.commun.Constante;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
/**
 * Class for the ReturnCode popup.
 * @author UBPartner
 *
 */
public class ReturnCodeReminder extends JDialog {

    /**
     * Define dialog.
     */
    public JDialog diaIndex = new JDialog();

    /**
     * Launch the application.
     * @param args - input arguments (normally empty).
     */
    public static void main(final String[] args) {
        ReturnCodeReminder dialog = new ReturnCodeReminder("mmi");
        dialog.diaIndex.setVisible(true);
    }

    /**
     * Constructor, create the dialog.
     * @param isPopup
     *            present whether generate log file pop up reminder.
     */
	public ReturnCodeReminder(final String isPopup) {
		if (!isPopup.equals(Constante.MMIPOPUP)) {
			if (isPopup.equals("y")) {
				initialization();
				diaIndex.setVisible(true);
			} else if (isPopup.equals("n")) {
				System.out.println(getText());
				return;
			}
		} else {
			return;
		}
	}

	/**
	 * Component initialization.
	 */
	private void initialization() {
		closeWindow(diaIndex);
		diaIndex.setResizable(false);
        diaIndex.setTitle(
                Constante.NOMAPPLICATION + " " + Constante.VERSIONAPPLICATION);
        diaIndex.setBounds(100, 100, 458, 254);
        diaIndex.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		diaIndex.getContentPane().setLayout(null);

		JPanel panelTitle = new JPanel();
		panelTitle.setLayout(null);
		panelTitle.setBounds(0, 0, 467, 40);
		Border loweredetched = new EtchedBorder(EtchedBorder.LOWERED);
        panelTitle.setBorder(loweredetched);
		diaIndex.getContentPane().add(panelTitle);

		JLabel labelTitle = new JLabel("Log Management");
		labelTitle.setFont(new Font("Tahoma", Font.BOLD, 14));
		labelTitle.setBounds(10, 11, 179, 17);
		panelTitle.add(labelTitle);

		JPanel panelContent = new JPanel();
		panelContent.setBounds(0, 39, 452, 144);
		panelContent.setBorder(loweredetched);
		diaIndex.getContentPane().add(panelContent);
		panelContent.setLayout(null);

		JTextArea textArea = new JTextArea();
		textArea.setFont(new Font("Tahoma", Font.PLAIN, 11));
		textArea.setEditable(false);
		textArea.setBounds(0, 0, 452, 144);
		textArea.setCaretColor(Color.BLACK);
		textArea.setText(getText());

		JScrollPane scrollPane = new JScrollPane(textArea);
		scrollPane.setBounds(0, 0, 452, 144);
		scrollPane.setVerticalScrollBarPolicy(
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setHorizontalScrollBarPolicy(
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		panelContent.add(scrollPane);

		JButton btnConfirm = new JButton("Confirm");
		btnConfirm.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(final MouseEvent arg0) {
				System.exit(-1);
			}
		});
		btnConfirm.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(final KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                	System.exit(-1);
                }
            }
        });
		btnConfirm.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnConfirm.setBounds(180, 194, 80, 23);
		diaIndex.getContentPane().add(btnConfirm);
	}

	/**
	 * Text area text getter.
	 * @return text area text.
	 */
	private static String getText() {
		String text = "Execution Results :\n\n";
		String returncodeLevel = LogManagement.getReturnCodeArr()[1];
		if (!returncodeLevel.equals("0")) {
			//text = text + returncodeLevel + " :\n";
			ArrayList<String[]> returncodeLevelMsgs = LogManagement.getLevelRelationFlagMsg();
			for (String[] message : returncodeLevelMsgs) {
				text = text + message[0] + " : " + message[1] + "\n"; 
			}
			text = text + "\n";
		} else {
			text = text + "Successful execution!\n\n";
		}
		String outputLogExplain = "";
		if (LMConfiguration.getLogfileActivation()) {
			File outputLog = new File(LMConfiguration.getOutpath());
			try {
				outputLogExplain = outputLogExplain + "Log file " + outputLog.getName()
		                + " has been generated in directory " + outputLog.getParentFile().getCanonicalPath() + ".\n";
			} catch (Exception e) {
				System.out.println("getText => " + e.getMessage());
			}
		}
		if (LMConfiguration.getPrintActivation()) {
			outputLogExplain = outputLogExplain + "Log messages have been printed out.\n";
		}
		text = text + outputLogExplain + "UBPARTNER strongly recommends you to verify it.\n";
		return text;
	}

	/**
     * Set ESC to close dialog.
     *
     * @param dialog
     *           dialog window to be closed.
     */
    private static void closeWindow(final JDialog dialog) {
        KeyStroke keystroke = KeyStroke
                .getKeyStroke(KeyEvent.VK_ESCAPE, 0);
        String dispatchWindowClosingActionMapKey =
                "com.spodding.tackline.dispatch:WINDOW_CLOSING";
        dialog.getRootPane().getInputMap(
                JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(keystroke, dispatchWindowClosingActionMapKey);
        dialog.getRootPane().getActionMap()
        .put(dispatchWindowClosingActionMapKey, new AbstractAction() {
            public void actionPerformed(final ActionEvent e) {
                System.exit(-1);
            }
        });
    }
}
