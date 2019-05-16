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

	public StudentInfo() { // default 생성자
	}

	public StudentInfo(String name, char gender, String id, String department, int age) {
		this.name = name;
		this.age = age;
		this.gender = gender;
		this.id = id;
		this.department = department;
		// 속성을 인자로 받아 설정하는 생성자
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
	public String toString() { // 정보를 String 형태로 반환하는 메서드
		if (gender == 'M')
			return String.format("%-15s%-15s%-15s%s\n", name + "(" + age + ")", "Male", id, department);
		else
			return String.format("%-15s%-15s%-15s%s\n", name + "(" + age + ")", "Female", id, department);
	}
}

class Util {
	static void makeInitDataFile(String fileName) { // 초기 data 파일을 생성하는 메서드입니다. "StudentData.dat"를 생성할 것입니다.
		try {
			ObjectOutputStream fileOut = new ObjectOutputStream(new FileOutputStream(fileName));
			// 파일 출력 스트림을 엽니다.

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
			// data가 담긴 HashMap을 생성합니다.

			fileOut.writeObject(students); // HashMap을 file에 씁니다.
			fileOut.close(); // 출력 스트림 닫기

		} catch (Exception e) {
			System.out.println("Problem with make inital data file " + fileName + ".");
			System.exit(0);
			// 예외 발생시 메시지 출력 후 프로그램 종료 합니다.
		}
	}

	static HashMap<String, StudentInfo> getHashMapinDataFile(String fileName) { // 파일로 부터 HashMap을 읽어오는 메서드.
		try {
			ObjectInputStream fileIn = new ObjectInputStream(new FileInputStream(fileName)); // InputStream 열기
			HashMap<String, StudentInfo> result = (HashMap<String, StudentInfo>) fileIn.readObject(); // HashMap 읽어오기
			fileIn.close();
			return result; // HashMap 반환.
		} catch (Exception e) {
			System.out.println("Problem with load data file.");
			System.exit(0); // 읽어오면서 예외 발생시에는 오류메시지 출력후 프로그램 종료
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

public class HW2_4_조예원 extends JFrame implements ActionListener {
	static String fileName = "StudentData.dat";
	JMenuItem saveM, searchM, insertM, deleteM, updateM; // 메뉴에 들어갈 아이템들
	JButton searchB, insertB, deleteB, updateB; // 상단 패널에 들어갈 버튼.
	resultPanel centerP;
	HashMap<String, StudentInfo> students;

	public JMenuItem makeMenu(String str, JMenu menu) {
		// 인자로 받은 문자열의 메뉴 아이템을 만들어서 메뉴에 붙이는 메서드 입니다. 또한 만든 아이템 인스턴스를 반환.
		JMenuItem temp = new JMenuItem(str);
		temp.addActionListener(this);
		menu.add(temp);
		return temp;
	}

	public JButton makeButton(String str, JPanel panel, ActionListener listner) {
		// 인자로 받은 문자열을 가진 버튼을 만들어서, 패널에 붙이는 것. 또한 해당 버튼 인스턴스를 반환
		JButton temp = new JButton(str);
		temp.addActionListener(listner);
		panel.add(temp);
		return temp;
	}

	public void printAllData(JTextArea textArea) { // HashMaP에 있는 모든 데이터를 인자로 받은 textArea에 출력하는 메서드입니다.
		textArea.setText(null); // 기존 정보 비우기
		Set<String> s = students.keySet(); // 학생 정보 하나씩 출력하기 위해 keySet()과 iterator사용
		Iterator<String> it = s.iterator();
		while (it.hasNext())
			textArea.append(students.get(it.next()).toString()); // 학생 한명씩 출력
	}

	static JTextArea makeTextArea() { // TextArea를 만들어서 반환하는 메서드 입니다.
		JTextArea temp = new JTextArea();
		temp.setFont(new Font("Monospaced", Font.PLAIN, 12));// format을 위해 Monospaced 폰트로 변경
		temp.setEditable(false); // 수정 불가
		return temp;
	}

	class resultPanel extends JPanel { // Frame의 Center에 붙여질 resultPanel입니다. (CardLayout임)
		CardLayout layout;
		SearchPanel searchP; // Search Panel
		InsertPanel insertP; // insert Panel
		DeletePanel deleteP;
		UpdatePanel updateP;

		class SearchPanel extends JPanel { // search 버튼을 눌렀을 때 나타탈 panel;
			JTextArea textArea; // 정보가 표시되는 곳
			JTextField keywardTF; // 검색 키워드 입력하는 곳
			JButton goB; // goB
			SearchActionListener actionListener = new SearchActionListener();

			public SearchPanel() {
				setLayout(new BorderLayout());

				JPanel textfieldP = new JPanel(); // 검색창이 있는 search의 삳단패널
				keywardTF = new JTextField(20);
				textfieldP.add(keywardTF); // 검색 키워드 입력할 TextField
				goB = makeButton("go", textfieldP, actionListener); // 검색버튼
				add(textfieldP, "North");

				textArea = makeTextArea();
				JScrollPane centerPanel = new JScrollPane(textArea);
				printAllData(textArea);
				add(centerPanel, "Center"); // searcP의 하단에 정보 텍스트 영역 붙이기
			}
		}

		class InsertPanel extends JPanel {
			JTextField nameTF, ageTF, idTF, departmentTF;
			JRadioButton maleRB, femaleRB;
			JButton insertB, cancelB;
			ActionListener actionListener;
			JTextArea textArea; // 정보가 표시괴는 곳.

			public void init() { // 패널의 textField와 radiobutton을 초기화하는 메서드.
				nameTF.setText("");
				ageTF.setText("");
				idTF.setText("");
				departmentTF.setText("");
				femaleRB.setSelected(true);
			}

			public InsertPanel(String button, ActionListener actionListener) {
				setLayout(new BorderLayout());
				this.actionListener=actionListener;

				// <<NorthPanel 만들기 시작>>
				JPanel northP = new JPanel(new BorderLayout());

				JPanel inputP = new JPanel(new GridLayout(5, 2)); // GridLayout으로 생성
				inputP.add(new JLabel("NAME"));
				nameTF = new JTextField();
				inputP.add(nameTF); // name Label과 TextField 붙이기
				inputP.add(new JLabel("AGE"));
				ageTF = new JTextField();
				inputP.add(ageTF); // Age Lable과 TextField 붙이기

				inputP.add(new JLabel("GENDER")); // gender label 붙이기
				JPanel radioP = new JPanel();
				maleRB = new JRadioButton("Male");
				femaleRB = new JRadioButton("Female");
				ButtonGroup bg = new ButtonGroup();
				bg.add(maleRB);
				bg.add(femaleRB); // 라디오 버튼 기능적으로 묶기
				radioP.add(maleRB);
				radioP.add(femaleRB);
				inputP.add(radioP);
				femaleRB.setSelected(true); // Female을 기본값으로

				inputP.add(new JLabel("ID"));
				idTF = new JTextField();
				inputP.add(idTF); // ID Lable과 TextField 붙이기
				inputP.add(new JLabel("DEPARTMENT"));
				departmentTF = new JTextField();
				inputP.add(departmentTF); // Department Lable과 TextField 붙이기
				northP.add(inputP, "Center");

				JPanel buttonP = new JPanel();
				insertB = makeButton(button, buttonP, actionListener);
				cancelB = makeButton("cancel", buttonP, actionListener);
				// insert와 cancel 버튼 생성후 Listener와 연결후 패널에 붙이기
				northP.add(buttonP, "South");

				add(northP, "North");
				// <<NorthPanel 만들기 끝>>

				// <<Center Panel 만들기 시작>>
				textArea = makeTextArea(); // textArea 생성
				JScrollPane centerP = new JScrollPane(textArea); // 스크롤 붙여서 패널 생성
				printAllData(textArea); // 학생 정보 데이터 나타나게 하기.
				add(centerP, "Center");
			}
		}

		class DeletePanel extends JPanel { // delete 메뉴 선택 했을 때,
			JTextField textField; // 삭제할 id 입력
			JButton delB; // 삭제 버튼
			JTextArea textArea; // 정보 출력하는 곳
			DeleteActionListener actionListener = new DeleteActionListener();

			public DeletePanel() {
				setLayout(new BorderLayout());

				JPanel northP = new JPanel(); // 삭제할 id 입력을 수행할 north패널
				textField = new JTextField(20);
				northP.add(textField);
				delB = makeButton("del", northP, actionListener); // 버튼 생성하고 Listener와 연결
				add(northP, "North");

				textArea = makeTextArea();
				JScrollPane centerP = new JScrollPane(textArea); // 스크롤 있는 textArea 생성
				printAllData(textArea); // 학생 정보 보이게하기
				add(centerP, "Center");
			}
		}

		class UpdatePanel extends JPanel {
			JTextField keywardTF;
			JButton goB;
			InsertPanel centerP; // 학생정보를 입력하고 출력하는 하단의 패널은 insert패널과 같은 형식이므로 재사용.
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

			searchP = new SearchPanel();// search 화면 만들기.
			add(searchP); // CardLayout인 패널에 붙이기
			layout.addLayoutComponent(searchP, "searchP"); // 컴포넌트도 추가해주기

			insertP = new InsertPanel("insert",new InsertActionListener()); // insert 화면 만들기.
			add(insertP);
			layout.addLayoutComponent(insertP, "insertP");

			deleteP = new DeletePanel(); // delete 화면 만들기
			add(deleteP);
			layout.addLayoutComponent(deleteP, "deleteP");

			updateP = new UpdatePanel();
			add(updateP);
			layout.addLayoutComponent(updateP, "updateP");
		}
	}

	public HW2_4_조예원() {
		setTitle("Students System");
		setSize(500, 500);
		setResizable(false); //사이즈 조정 불가
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		// 파일에서 데이터 읽어오기
		students = Util.getHashMapinDataFile(fileName);

		// <<MenuBar 만들기 시작>>
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
		// <MenuBar 만들기 끝>>

		// <<상단 Panel 만들기 시작>>
		JPanel northP = new JPanel();
		searchB = makeButton("Search", northP, this);
		insertB = makeButton("Insert", northP, this);
		deleteB = makeButton("Delete", northP, this);
		updateB = makeButton("Update", northP, this);
		add(northP, "North");
		// <상단 Panel 만들기 종료>>

		// <<하단 Panel 만들기 시작>>
		centerP = new resultPanel();
		add(centerP, "Center");
		// <<하단 Panel 만들기 끝>>

		setVisible(true);
	}

	public void actionPerformed(ActionEvent e) { // 여기서는 메뉴바와 상단 패널의 버튼(Search,insert,delete,update)에 대한 event 처리
		if (e.getSource() == searchB || e.getSource() == searchM) { // searchMenu를 선택했을때.
			centerP.searchP.keywardTF.setText(""); // 검색어 TextField 빈상태로.
			printAllData(centerP.searchP.textArea);
			centerP.layout.show(centerP, "searchP"); // searchPanel 보여주기
		} else if (e.getSource() == insertB || e.getSource() == insertM) // 사용자가 insertMenu를 선택했을때,
			centerP.layout.show(centerP, "insertP");
		else if (e.getSource() == deleteB || e.getSource() == deleteM) // 사용자가 deleteMenu를 선택했을때,
			centerP.layout.show(centerP, "deleteP");
		else if (e.getSource() == updateB || e.getSource() == updateM) { // 사용자가 updateMenu를 선택했을때,
			centerP.updateP.init(); //창 초기화
			centerP.layout.show(centerP, "updateP");
		}
		else if(e.getSource()==saveM) { //사용자가 save 버튼을 눌렀을때,
			Util.fileUpdate(fileName, students);
		}
	}

	class SearchActionListener implements ActionListener { // search 메뉴에서의 이벤트 처리
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == centerP.searchP.goB) { // 사용자가 검색 버튼을 눌렀을때,
				String keyward = centerP.searchP.keywardTF.getText();// 검색어 받아오기
				StudentInfo temp;

				centerP.searchP.textArea.setText(null); // 기존 정보 비우기.
				Set<String> s = students.keySet(); // 학생 정보 하나씩 확인을 위해 keySet()과 iterator사용
				Iterator<String> it = s.iterator();
				while (it.hasNext()) {
					temp = students.get(it.next()); // 학생 한명씩 정보 받기.
					if (temp.getName().equals(keyward)||temp.getId().equals(keyward)||temp.getDepartment().equals(keyward)) // 만약 해당 정보가 검색어를 포함하고 있으면
						centerP.searchP.textArea.append(temp.toString()); // textArea에 출력하기.
				}
				if (centerP.searchP.textArea.getText().trim().length() == 0)
					centerP.searchP.textArea.append("No Result\n");
			}
		}
	}

	class InsertActionListener implements ActionListener { // Insert 메뉴에서의 이벤트 처리
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == centerP.insertP.insertB) { // insert 버튼이 눌려졌을때.
				StudentInfo temp = new StudentInfo(centerP.insertP.nameTF.getText(),
						centerP.insertP.femaleRB.isSelected() ? 'F' : 'M', centerP.insertP.idTF.getText(),
						centerP.insertP.departmentTF.getText(), Integer.parseInt(centerP.insertP.ageTF.getText()));
				// 사용자가 적은 정보로 StudentInfo 인스턴스 생성.
				students.put(centerP.insertP.idTF.getText(), temp); // HashMap에 넣기.
				centerP.insertP.init(); // 스크린 초기화
				printAllData(centerP.insertP.textArea); // 추가된 데이터를 포함한 학생 정보 다시 출력
			} else if (e.getSource() == centerP.insertP.cancelB) { // cancel 버튼이 눌러졌을때,
				centerP.insertP.init(); // 스크린 초기화
			}
		}
	}

	class DeleteActionListener implements ActionListener { // Delete 메뉴에서의 이벤트 처리
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == centerP.deleteP.delB) { // delete 버튼이 눌러졌을때,
				String delId = centerP.deleteP.textField.getText(); // 사용자가 입력한 id 가져오기
				if (students.containsKey(delId)) { // 만약 HashMap에 해당 id를 key로 하는 것이 있다면.
					students.remove(delId); // 지우기
					printAllData(centerP.deleteP.textArea); // 갱신한 정보로 다시 출력
				}
				centerP.deleteP.textField.setText(""); // textField 빈상태로 초기화.
			}
		}
	}

	class UpdateActionListener implements ActionListener { // update 메뉴에서의 이벤트 처리
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
				String keyward = centerP.updateP.keywardTF.getText();// 검색어 받아오기
				StudentInfo temp;

				centerP.updateP.centerP.textArea.setText(null); // 기존 정보 비우기
				Set<String> s = students.keySet(); // 학생 정보 하나씩 확인을 위해 keySet()과 iterator사용
				Iterator<String> it = s.iterator();
				while (it.hasNext()) {
					temp = students.get(it.next()); // 학생 한명씩 정보 받기.
					if (temp.getName().equals(keyward)||temp.getId().equals(keyward)||temp.getDepartment().equals(keyward)) { // 만약 해당 정보가 검색어를 포함하고 있으면
						student=temp; //클래스 변수에 저장하기.
						centerP.updateP.centerP.textArea.append(temp.toString()); // textArea에 출력하기.
						fullAuto(); //해당 학생의 정보로 채우기.
					}
				}
				if (centerP.updateP.centerP.textArea.getText().trim().length() == 0)
					centerP.updateP.centerP.textArea.append("No Result\n"); //검색 실패시.
			} else if (e.getSource() == centerP.updateP.centerP.insertB) { // update버튼 눌러지면
				if(student!=null) {
					students.remove(student.getId());//원래 있던 학생 정보를 HashMap에서 삭제
					
					if (centerP.updateP.keywardTF.getText().equals(student.getName()))
						centerP.updateP.keywardTF.setText(centerP.updateP.centerP.nameTF.getText());
					else if(centerP.updateP.keywardTF.getText().equals(student.getId()))
						centerP.updateP.keywardTF.setText(centerP.updateP.centerP.idTF.getText());
					else centerP.updateP.keywardTF.setText(centerP.updateP.centerP.departmentTF.getText());
					//사용자의 검색어도 새로운 정보로 업데이트. 이름을 검색했다면 새로 바뀐 이름으로, id로 검색했따면 새로 바뀐 아이디로 검색어 변경
					
					student=new StudentInfo(centerP.updateP.centerP.nameTF.getText(),
							centerP.updateP.centerP.femaleRB.isSelected()? 'F':'M',
									centerP.updateP.centerP.idTF.getText(),
									centerP.updateP.centerP.departmentTF.getText(),
									Integer.parseInt(centerP.updateP.centerP.ageTF.getText()));
					students.put(student.getId(),student); //사용자의 입력으로 새로운 StudentInfo를 생성 후 HashMap에 넣기.
					centerP.updateP.centerP.textArea.setText(null); // 기존 정보 비우기
					centerP.updateP.centerP.textArea.append(student.toString()); //바꾼 정보 보여주기
					
				}
			}
			else if(e.getSource()==centerP.updateP.centerP.cancelB) {
				if(student!=null) {
					fullAuto(); //다시 원래 정보 채우기
				}
			}
		}
	}

	public static void main(String[] args) {
		//Util.makeInitDataFile(fileName); // 초기 데이터를 담은 파일 생성하기. 처음 한번만 이용하기
		
		System.out.println("============");
		System.out.println("전공: 컴퓨터공학과");
		System.out.println("학번: 1771104");
		System.out.println("이름: 조예원");
		System.out.println("============");
		
		new HW2_4_조예원();
	}
}
