import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

class Contact {
    private String name;
    private String phoneNumber;
    private String email;

    public Contact(String name, String phoneNumber, String email) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "Name: " + name + ", Phone Number: " + phoneNumber + ", Email: " + email;
    }
}

class AddressBook {
    private List<Contact> contacts;

    public AddressBook() {
        contacts = new ArrayList<>();
    }

    public void addContact(Contact contact) {
        contacts.add(contact);
    }

    public List<Contact> getAllContacts() {
        return contacts;
    }

    public Contact getContactByName(String name) {
        for (Contact contact : contacts) {
            if (contact.getName().equalsIgnoreCase(name)) {
                return contact;
            }
        }
        return null;
    }
}

public class AddressBookSystemGUI extends JFrame {
    private AddressBook addressBook;
    private DefaultListModel<String> contactsListModel;
    private JList<String> contactsList;
    private JTextField nameField;
    private JTextField phoneField;
    private JTextField emailField;
    private JButton addButton;
    private JButton searchButton;

    public AddressBookSystemGUI() {
        addressBook = new AddressBook();

        setTitle("Address Book System");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        contactsListModel = new DefaultListModel<>();
        contactsList = new JList<>(contactsListModel);
        JScrollPane scrollPane = new JScrollPane(contactsList);

        nameField = new JTextField(20);
        phoneField = new JTextField(20);
        emailField = new JTextField(20);

        addButton = new JButton("Add Contact");
        searchButton = new JButton("Search Contact");

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String phoneNumber = phoneField.getText();
                String email = emailField.getText();

                Contact newContact = new Contact(name, phoneNumber, email);
                addressBook.addContact(newContact);
                updateContactsList();
                clearFields();
            }
        });

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchName = nameField.getText();
                Contact foundContact = addressBook.getContactByName(searchName);
                if (foundContact != null) {
                    JOptionPane.showMessageDialog(AddressBookSystemGUI.this,
                            "Found contact:\n" + foundContact, "Contact Found", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(AddressBookSystemGUI.this,
                            "Contact not found!", "Contact Not Found", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        inputPanel.add(new JLabel("Name:"));
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("Phone Number:"));
        inputPanel.add(phoneField);
        inputPanel.add(new JLabel("Email:"));
        inputPanel.add(emailField);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(addButton);
        buttonPanel.add(searchButton);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(inputPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private void updateContactsList() {
        contactsListModel.clear();
        List<Contact> allContacts = addressBook.getAllContacts();
        for (Contact contact : allContacts) {
            contactsListModel.addElement(contact.getName());
        }
    }

    private void clearFields() {
        nameField.setText("");
        phoneField.setText("");
        emailField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new AddressBookSystemGUI().setVisible(true);
            }
        });
    }
}