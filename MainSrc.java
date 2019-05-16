import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.awt.event.*;
import javax.swing.*;
import java.awt.*;

class StudentInfo implements Serializable {
	private String name, id, department;
	private int age;
	private char gender;

	public StudentInfo() { // default ������
	}

	public StudentInfo(String name, char gender, String id, String department, int age) {
		this.name = name;
		this.age = age;
		this.gender = gender;
		this.id = id;
		this.department = department;
		// �Ӽ��� ���ڷ� �޾� �����ϴ� ������
	}

	public String getName() {
		return name;
	}

	public String getId() {
		return id;
	}

	public String getDepartment() {
		return department;
	}

	public int getAge() {
		return age;
	}

	public char getGender() {
		return gender;
	}

	@Override
	public String toString() { // ������ String ���·� ��ȯ�ϴ� �޼���
		if (gender == 'M')
			return String.format("%-15s%-15s%-15s%s\n", name + "(" + age + ")", "Male", id, department);
		else
			return String.format("%-15s%-15s%-15s%s\n", name + "(" + age + ")", "Female", id, department);
	}
}

class Util {
	static void makeInitDataFile(String fileName) { // �ʱ� data ������ �����ϴ� �޼����Դϴ�. "StudentData.dat"�� ������ ���Դϴ�.
		try {
			ObjectOutputStream fileOut = new ObjectOutputStream(new FileOutputStream(fileName));
			// ���� ��� ��Ʈ���� ���ϴ�.

			HashMap<String, StudentInfo> students = new HashMap<String, StudentInfo>();
			students.put("48544567", new StudentInfo("Callia", 'F', "48544567", "Economics", 20));
			students.put("65001422", new StudentInfo("Eldora", 'F', "65001422", "Materials Engineering", 21));
			students.put("78538007", new StudentInfo("Henry", 'M', "78538007", "Computer Science", 22));
			students.put("35647567", new StudentInfo("Kenneth", 'F', "35647567", "Computer Science", 25));
			students.put("23458211", new StudentInfo("Martha", 'M', "23458211", "Materials Engineering", 28));
			students.put("63559848", new StudentInfo("Ralph", 'M', "63559848", "Materials Engineering", 23));
			students.put("32544311", new StudentInfo("Huey", 'M', "32544311", "Korean Language and Literature", 24));
			students.put("23396621", new StudentInfo("Remy", 'F', "23396621", "Architecture", 20));
			students.put("43253688", new StudentInfo("Sasha", 'F', "43253688", "Architecture", 22));
			students.put("65479987", new StudentInfo("Tess", 'M', "65479987", "Architecture", 23));
			students.put("85251544", new StudentInfo("Velika", 'F', "85251544", "Korean Language and Literature", 25));
			students.put("41996755", new StudentInfo("Zelia", 'F', "41996755", "Electronics Engineering", 23));
			students.put("26144539", new StudentInfo("Brian", 'M', "26144539", "Electronics Engineering", 22));
			students.put("18538799", new StudentInfo("Sylvia ", 'F', "18538799", "Economics", 21));
			students.put("60110022", new StudentInfo("Tyler", 'M', "60110022", "Electronics Engineering", 20));
			students.put("65382543", new StudentInfo("Simon", 'M', "65382543", "Computer Science", 23));
			students.put("83564337", new StudentInfo("Primo", 'M', "83564337", "Electronics Engineering", 22));
			students.put("60150991", new StudentInfo("Patricia", 'F', "60150991", "Computer Science", 21));
			students.put("31796819", new StudentInfo("Olivia", 'F', "31796819", "Computer Science", 24));
			students.put("08679009", new StudentInfo("Paul", 'M', "08679009", "Korean Language and Literature", 22));
			// data�� ��� HashMap�� �����մϴ�.

			fileOut.writeObject(students); // HashMap�� file�� ���ϴ�.
			fileOut.close(); // ��� ��Ʈ�� �ݱ�

		} catch (Exception e) {
			System.out.println("Problem with make inital data file " + fileName + ".");
			System.exit(0);
			// ���� �߻��� �޽��� ��� �� ���α׷� ���� �մϴ�.
		}
	}

	static HashMap<String, StudentInfo> getHashMapinDataFile(String fileName) { // ���Ϸ� ���� HashMap�� �о���� �޼���.
		try {
			ObjectInputStream fileIn = new ObjectInputStream(new FileInputStream(fileName)); // InputStream ����
			HashMap<String, StudentInfo> result = (HashMap<String, StudentInfo>) fileIn.readObject(); // HashMap �о����
			fileIn.close();
			return result; // HashMap ��ȯ.
		} catch (Exception e) {
			System.out.println("Problem with load data file.");
			System.exit(0); // �о���鼭 ���� �߻��ÿ��� �����޽��� ����� ���α׷� ����
			return null;
		}
	}
	
	static void fileUpdate(String fileName, HashMap<String, StudentInfo> data) {
		try {
		ObjectOutputStream fileOut=new ObjectOutputStream(new FileOutputStream(fileName));
		fileOut.writeObject(data);
		fileOut.close();
		}
		catch(Exception e) {
			System.out.println("Problem with save data file " + fileName + ".");
			System.exit(0);
		}
	}
}

public class HW2_4_������ extends JFrame implements ActionListener {
	static String fileName = "StudentData.dat";
	JMenuItem saveM, searchM, insertM, deleteM, updateM; // �޴��� �� �����۵�
	JButton searchB, insertB, deleteB, updateB; // ��� �гο� �� ��ư.
	resultPanel centerP;
	HashMap<String, StudentInfo> students;

	public JMenuItem makeMenu(String str, JMenu menu) {
		// ���ڷ� ���� ���ڿ��� �޴� �������� ���� �޴��� ���̴� �޼��� �Դϴ�. ���� ���� ������ �ν��Ͻ��� ��ȯ.
		JMenuItem temp = new JMenuItem(str);
		temp.addActionListener(this);
		menu.add(temp);
		return temp;
	}

	public JButton makeButton(String str, JPanel panel, ActionListener listner) {
		// ���ڷ� ���� ���ڿ��� ���� ��ư�� ����, �гο� ���̴� ��. ���� �ش� ��ư �ν��Ͻ��� ��ȯ
		JButton temp = new JButton(str);
		temp.addActionListener(listner);
		panel.add(temp);
		return temp;
	}

	public void printAllData(JTextArea textArea) { // HashMaP�� �ִ� ��� �����͸� ���ڷ� ���� textArea�� ����ϴ� �޼����Դϴ�.
		textArea.setText(null); // ���� ���� ����
		Set<String> s = students.keySet(); // �л� ���� �ϳ��� ����ϱ� ���� keySet()�� iterator���
		Iterator<String> it = s.iterator();
		while (it.hasNext())
			textArea.append(students.get(it.next()).toString()); // �л� �Ѹ� ���
	}

	static JTextArea makeTextArea() { // TextArea�� ���� ��ȯ�ϴ� �޼��� �Դϴ�.
		JTextArea temp = new JTextArea();
		temp.setFont(new Font("Monospaced", Font.PLAIN, 12));// format�� ���� Monospaced ��Ʈ�� ����
		temp.setEditable(false); // ���� �Ұ�
		return temp;
	}

	class resultPanel extends JPanel { // Frame�� Center�� �ٿ��� resultPanel�Դϴ�. (CardLayout��)
		CardLayout layout;
		SearchPanel searchP; // Search Panel
		InsertPanel insertP; // insert Panel
		DeletePanel deleteP;
		UpdatePanel updateP;

		class SearchPanel extends JPanel { // search ��ư�� ������ �� ��ŸŻ panel;
			JTextArea textArea; // ������ ǥ�õǴ� ��
			JTextField keywardTF; // �˻� Ű���� �Է��ϴ� ��
			JButton goB; // goB
			SearchActionListener actionListener = new SearchActionListener();

			public SearchPanel() {
				setLayout(new BorderLayout());

				JPanel textfieldP = new JPanel(); // �˻�â�� �ִ� search�� ����г�
				keywardTF = new JTextField(20);
				textfieldP.add(keywardTF); // �˻� Ű���� �Է��� TextField
				goB = makeButton("go", textfieldP, actionListener); // �˻���ư
				add(textfieldP, "North");

				textArea = makeTextArea();
				JScrollPane centerPanel = new JScrollPane(textArea);
				printAllData(textArea);
				add(centerPanel, "Center"); // searcP�� �ϴܿ� ���� �ؽ�Ʈ ���� ���̱�
			}
		}

		class InsertPanel extends JPanel {
			JTextField nameTF, ageTF, idTF, departmentTF;
			JRadioButton maleRB, femaleRB;
			JButton insertB, cancelB;
			ActionListener actionListener;
			JTextArea textArea; // ������ ǥ�ñ��� ��.

			public void init() { // �г��� textField�� radiobutton�� �ʱ�ȭ�ϴ� �޼���.
				nameTF.setText("");
				ageTF.setText("");
				idTF.setText("");
				departmentTF.setText("");
				femaleRB.setSelected(true);
			}

			public InsertPanel(String button, ActionListener actionListener) {
				setLayout(new BorderLayout());
				this.actionListener=actionListener;

				// <<NorthPanel ����� ����>>
				JPanel northP = new JPanel(new BorderLayout());

				JPanel inputP = new JPanel(new GridLayout(5, 2)); // GridLayout���� ����
				inputP.add(new JLabel("NAME"));
				nameTF = new JTextField();
				inputP.add(nameTF); // name Label�� TextField ���̱�
				inputP.add(new JLabel("AGE"));
				ageTF = new JTextField();
				inputP.add(ageTF); // Age Lable�� TextField ���̱�

				inputP.add(new JLabel("GENDER")); // gender label ���̱�
				JPanel radioP = new JPanel();
				maleRB = new JRadioButton("Male");
				femaleRB = new JRadioButton("Female");
				ButtonGroup bg = new ButtonGroup();
				bg.add(maleRB);
				bg.add(femaleRB); // ���� ��ư ��������� ����
				radioP.add(maleRB);
				radioP.add(femaleRB);
				inputP.add(radioP);
				femaleRB.setSelected(true); // Female�� �⺻������

				inputP.add(new JLabel("ID"));
				idTF = new JTextField();
				inputP.add(idTF); // ID Lable�� TextField ���̱�
				inputP.add(new JLabel("DEPARTMENT"));
				departmentTF = new JTextField();
				inputP.add(departmentTF); // Department Lable�� TextField ���̱�
				northP.add(inputP, "Center");

				JPanel buttonP = new JPanel();
				insertB = makeButton(button, buttonP, actionListener);
				cancelB = makeButton("cancel", buttonP, actionListener);
				// insert�� cancel ��ư ������ Listener�� ������ �гο� ���̱�
				northP.add(buttonP, "South");

				add(northP, "North");
				// <<NorthPanel ����� ��>>

				// <<Center Panel ����� ����>>
				textArea = makeTextArea(); // textArea ����
				JScrollPane centerP = new JScrollPane(textArea); // ��ũ�� �ٿ��� �г� ����
				printAllData(textArea); // �л� ���� ������ ��Ÿ���� �ϱ�.
				add(centerP, "Center");
			}
		}

		class DeletePanel extends JPanel { // delete �޴� ���� ���� ��,
			JTextField textField; // ������ id �Է�
			JButton delB; // ���� ��ư
			JTextArea textArea; // ���� ����ϴ� ��
			DeleteActionListener actionListener = new DeleteActionListener();

			public DeletePanel() {
				setLayout(new BorderLayout());

				JPanel northP = new JPanel(); // ������ id �Է��� ������ north�г�
				textField = new JTextField(20);
				northP.add(textField);
				delB = makeButton("del", northP, actionListener); // ��ư �����ϰ� Listener�� ����
				add(northP, "North");

				textArea = makeTextArea();
				JScrollPane centerP = new JScrollPane(textArea); // ��ũ�� �ִ� textArea ����
				printAllData(textArea); // �л� ���� ���̰��ϱ�
				add(centerP, "Center");
			}
		}

		class UpdatePanel extends JPanel {
			JTextField keywardTF;
			JButton goB;
			InsertPanel centerP; // �л������� �Է��ϰ� ����ϴ� �ϴ��� �г��� insert�гΰ� ���� �����̹Ƿ� ����.
			UpdateActionListener actionListener = new UpdateActionListener();
			
			public void init() {
				keywardTF.setText("");
				centerP.idTF.setText("");
				centerP.ageTF.setText("");
				centerP.femaleRB.setSelected(true);
				centerP.nameTF.setText("");
				centerP.departmentTF.setText("");
				printAllData(centerP.textArea);
			}
			public UpdatePanel() {
				setLayout(new BorderLayout());

				JPanel northP = new JPanel();
				keywardTF = new JTextField(20);
				northP.add(keywardTF);
				goB = makeButton("GO", northP, actionListener);
				add(northP, "North");

				centerP = new InsertPanel("update",actionListener);
				add(centerP, "Center");
			}
		}

		public resultPanel() {
			layout = new CardLayout();
			setLayout(layout);

			searchP = new SearchPanel();// search ȭ�� �����.
			add(searchP); // CardLayout�� �гο� ���̱�
			layout.addLayoutComponent(searchP, "searchP"); // ������Ʈ�� �߰����ֱ�

			insertP = new InsertPanel("insert",new InsertActionListener()); // insert ȭ�� �����.
			add(insertP);
			layout.addLayoutComponent(insertP, "insertP");

			deleteP = new DeletePanel(); // delete ȭ�� �����
			add(deleteP);
			layout.addLayoutComponent(deleteP, "deleteP");

			updateP = new UpdatePanel();
			add(updateP);
			layout.addLayoutComponent(updateP, "updateP");
		}
	}

	public HW2_4_������() {
		setTitle("Students System");
		setSize(500, 500);
		setResizable(false); //������ ���� �Ұ�
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		// ���Ͽ��� ������ �о����
		students = Util.getHashMapinDataFile(fileName);

		// <<MenuBar ����� ����>>
		JMenuBar menuBar = new JMenuBar();

		JMenu fileMenu = new JMenu("File");
		saveM = makeMenu("Save", fileMenu);
		menuBar.add(fileMenu);

		JMenu editMenu = new JMenu("Edit");
		searchM = makeMenu("Search", editMenu);
		insertM = makeMenu("Insert", editMenu);
		deleteM = makeMenu("Delete", editMenu);
		updateM = makeMenu("Update", editMenu);
		menuBar.add(editMenu);

		setJMenuBar(menuBar);
		// <MenuBar ����� ��>>

		// <<��� Panel ����� ����>>
		JPanel northP = new JPanel();
		searchB = makeButton("Search", northP, this);
		insertB = makeButton("Insert", northP, this);
		deleteB = makeButton("Delete", northP, this);
		updateB = makeButton("Update", northP, this);
		add(northP, "North");
		// <��� Panel ����� ����>>

		// <<�ϴ� Panel ����� ����>>
		centerP = new resultPanel();
		add(centerP, "Center");
		// <<�ϴ� Panel ����� ��>>

		setVisible(true);
	}

	public void actionPerformed(ActionEvent e) { // ���⼭�� �޴��ٿ� ��� �г��� ��ư(Search,insert,delete,update)�� ���� event ó��
		if (e.getSource() == searchB || e.getSource() == searchM) { // searchMenu�� ����������.
			centerP.searchP.keywardTF.setText(""); // �˻��� TextField ����·�.
			printAllData(centerP.searchP.textArea);
			centerP.layout.show(centerP, "searchP"); // searchPanel �����ֱ�
		} else if (e.getSource() == insertB || e.getSource() == insertM) // ����ڰ� insertMenu�� ����������,
			centerP.layout.show(centerP, "insertP");
		else if (e.getSource() == deleteB || e.getSource() == deleteM) // ����ڰ� deleteMenu�� ����������,
			centerP.layout.show(centerP, "deleteP");
		else if (e.getSource() == updateB || e.getSource() == updateM) { // ����ڰ� updateMenu�� ����������,
			centerP.updateP.init(); //â �ʱ�ȭ
			centerP.layout.show(centerP, "updateP");
		}
		else if(e.getSource()==saveM) { //����ڰ� save ��ư�� ��������,
			Util.fileUpdate(fileName, students);
		}
	}

	class SearchActionListener implements ActionListener { // search �޴������� �̺�Ʈ ó��
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == centerP.searchP.goB) { // ����ڰ� �˻� ��ư�� ��������,
				String keyward = centerP.searchP.keywardTF.getText();// �˻��� �޾ƿ���
				StudentInfo temp;

				centerP.searchP.textArea.setText(null); // ���� ���� ����.
				Set<String> s = students.keySet(); // �л� ���� �ϳ��� Ȯ���� ���� keySet()�� iterator���
				Iterator<String> it = s.iterator();
				while (it.hasNext()) {
					temp = students.get(it.next()); // �л� �Ѹ� ���� �ޱ�.
					if (temp.getName().equals(keyward)||temp.getId().equals(keyward)||temp.getDepartment().equals(keyward)) // ���� �ش� ������ �˻�� �����ϰ� ������
						centerP.searchP.textArea.append(temp.toString()); // textArea�� ����ϱ�.
				}
				if (centerP.searchP.textArea.getText().trim().length() == 0)
					centerP.searchP.textArea.append("No Result\n");
			}
		}
	}

	class InsertActionListener implements ActionListener { // Insert �޴������� �̺�Ʈ ó��
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == centerP.insertP.insertB) { // insert ��ư�� ����������.
				StudentInfo temp = new StudentInfo(centerP.insertP.nameTF.getText(),
						centerP.insertP.femaleRB.isSelected() ? 'F' : 'M', centerP.insertP.idTF.getText(),
						centerP.insertP.departmentTF.getText(), Integer.parseInt(centerP.insertP.ageTF.getText()));
				// ����ڰ� ���� ������ StudentInfo �ν��Ͻ� ����.
				students.put(centerP.insertP.idTF.getText(), temp); // HashMap�� �ֱ�.
				centerP.insertP.init(); // ��ũ�� �ʱ�ȭ
				printAllData(centerP.insertP.textArea); // �߰��� �����͸� ������ �л� ���� �ٽ� ���
			} else if (e.getSource() == centerP.insertP.cancelB) { // cancel ��ư�� ����������,
				centerP.insertP.init(); // ��ũ�� �ʱ�ȭ
			}
		}
	}

	class DeleteActionListener implements ActionListener { // Delete �޴������� �̺�Ʈ ó��
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == centerP.deleteP.delB) { // delete ��ư�� ����������,
				String delId = centerP.deleteP.textField.getText(); // ����ڰ� �Է��� id ��������
				if (students.containsKey(delId)) { // ���� HashMap�� �ش� id�� key�� �ϴ� ���� �ִٸ�.
					students.remove(delId); // �����
					printAllData(centerP.deleteP.textArea); // ������ ������ �ٽ� ���
				}
				centerP.deleteP.textField.setText(""); // textField ����·� �ʱ�ȭ.
			}
		}
	}

	class UpdateActionListener implements ActionListener { // update �޴������� �̺�Ʈ ó��
		StudentInfo student=null;
		
		public void fullAuto() {
			if(student!=null) {
				centerP.updateP.centerP.nameTF.setText(student.getName());
				centerP.updateP.centerP.ageTF.setText("" + student.getAge());
				if (student.getGender() == 'F')
					centerP.updateP.centerP.femaleRB.setSelected(true);
				else
					centerP.updateP.centerP.maleRB.setSelected(true);
				centerP.updateP.centerP.idTF.setText(student.getId());
				centerP.updateP.centerP.departmentTF.setText(student.getDepartment());
			}
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == centerP.updateP.goB) {
				String keyward = centerP.updateP.keywardTF.getText();// �˻��� �޾ƿ���
				StudentInfo temp;

				centerP.updateP.centerP.textArea.setText(null); // ���� ���� ����
				Set<String> s = students.keySet(); // �л� ���� �ϳ��� Ȯ���� ���� keySet()�� iterator���
				Iterator<String> it = s.iterator();
				while (it.hasNext()) {
					temp = students.get(it.next()); // �л� �Ѹ� ���� �ޱ�.
					if (temp.getName().equals(keyward)||temp.getId().equals(keyward)||temp.getDepartment().equals(keyward)) { // ���� �ش� ������ �˻�� �����ϰ� ������
						student=temp; //Ŭ���� ������ �����ϱ�.
						centerP.updateP.centerP.textArea.append(temp.toString()); // textArea�� ����ϱ�.
						fullAuto(); //�ش� �л��� ������ ä���.
					}
				}
				if (centerP.updateP.centerP.textArea.getText().trim().length() == 0)
					centerP.updateP.centerP.textArea.append("No Result\n"); //�˻� ���н�.
			} else if (e.getSource() == centerP.updateP.centerP.insertB) { // update��ư ��������
				if(student!=null) {
					students.remove(student.getId());//���� �ִ� �л� ������ HashMap���� ����
					
					if (centerP.updateP.keywardTF.getText().equals(student.getName()))
						centerP.updateP.keywardTF.setText(centerP.updateP.centerP.nameTF.getText());
					else if(centerP.updateP.keywardTF.getText().equals(student.getId()))
						centerP.updateP.keywardTF.setText(centerP.updateP.centerP.idTF.getText());
					else centerP.updateP.keywardTF.setText(centerP.updateP.centerP.departmentTF.getText());
					//������� �˻�� ���ο� ������ ������Ʈ. �̸��� �˻��ߴٸ� ���� �ٲ� �̸�����, id�� �˻��ߵ��� ���� �ٲ� ���̵�� �˻��� ����
					
					student=new StudentInfo(centerP.updateP.centerP.nameTF.getText(),
							centerP.updateP.centerP.femaleRB.isSelected()? 'F':'M',
									centerP.updateP.centerP.idTF.getText(),
									centerP.updateP.centerP.departmentTF.getText(),
									Integer.parseInt(centerP.updateP.centerP.ageTF.getText()));
					students.put(student.getId(),student); //������� �Է����� ���ο� StudentInfo�� ���� �� HashMap�� �ֱ�.
					centerP.updateP.centerP.textArea.setText(null); // ���� ���� ����
					centerP.updateP.centerP.textArea.append(student.toString()); //�ٲ� ���� �����ֱ�
					
				}
			}
			else if(e.getSource()==centerP.updateP.centerP.cancelB) {
				if(student!=null) {
					fullAuto(); //�ٽ� ���� ���� ä���
				}
			}
		}
	}

	public static void main(String[] args) {
		//Util.makeInitDataFile(fileName); // �ʱ� �����͸� ���� ���� �����ϱ�. ó�� �ѹ��� �̿��ϱ�
		
		System.out.println("============");
		System.out.println("����: ��ǻ�Ͱ��а�");
		System.out.println("�й�: 1771104");
		System.out.println("�̸�: ������");
		System.out.println("============");
		
		new HW2_4_������();
	}
}
