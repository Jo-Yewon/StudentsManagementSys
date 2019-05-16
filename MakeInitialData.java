import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

public class HW_2_4_초기데이터생성 {
	public static void main(String[] args) {
		System.out.println("============");
		System.out.println("전공: 컴퓨터공학과");
		System.out.println("학번: 1771104");
		System.out.println("이름: 조예원");
		System.out.println("============");
		
		String fileName = "StudentData.dat";
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
		System.out.println("초기 데이터 파일 \""+fileName+"\"만들기 성공");
	}
}
