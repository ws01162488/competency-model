
public class TextTest {

	public static void main(String[] args) {
		String s = "有终局思维；\\n能敏锐地洞察社会、行业以及市场等的新动向、新趋势；\\n能带动中高层人员的思考意识；";
		System.out.println(s.replaceAll("\\\\n", "<br/>"));
	}

}
