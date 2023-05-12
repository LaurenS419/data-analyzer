package finalproject;

import java.util.ArrayList;

public class RatingByGender extends DataAnalyzer{

	private MyHashTable<String, MyHashTable<String, MyHashTable<String, Integer>>> master;
	//private MyHashTable<String, MyHashTable <String, Integer>> qualityHash;
	//private MyHashTable<String, MyHashTable <String, Integer>> diffHash;
	private MyHashTable<String, Integer> table;

	public RatingByGender(Parser p) {
		super(p);
	}

	@Override
	public MyHashTable<String, Integer> getDistByKeyword(String keyword) {

		String kWord = keyword.substring(2);
		String gender = Character.toString(keyword.charAt(0));

		//System.out.println(this.master.get(kWord).size());
		//System.out.println(this.master.get(kWord).get(gender).size());

		this.table = this.master.get(kWord).get(gender);

		return this.table;
	}

	@Override
	public void extractInformation() {
		// Set Up //
		this.master = new MyHashTable<>();
		this.table = new MyHashTable<String, Integer>();
		MyHashTable<String, MyHashTable <String, Integer>> qualHash = new MyHashTable<>();
		MyHashTable<String, MyHashTable <String, Integer>> diffHash = new MyHashTable<>();

		// Set Up //
		int gIndex = parser.fields.get("gender");
		int qIndex = parser.fields.get("student_star");
		int dIndex = parser.fields.get("student_difficult");

		String[] genders = new String[parser.data.size()];
		String[] qualities = new String[parser.data.size()];
		String[] difficulties = new String[parser.data.size()];

		for(int j = 0; j < parser.data.size(); j++){
			genders[j] = parser.data.get(j)[gIndex];
			qualities[j] = parser.data.get(j)[qIndex];
			difficulties[j] = parser.data.get(j)[dIndex];
		}


		//QUALITY
		for(int i = 0; i < parser.data.size(); i++) {
			String gender = genders[i];
			String rating = qualities[i];
			rating = Integer.toString((int) Double.parseDouble(rating));

			if(qualHash.get(gender) == null){
				MyHashTable<String, Integer> value = new MyHashTable<>();
				value.put("1", 0);
				value.put("2", 0);
				value.put("3", 0);
				value.put("4", 0);
				value.put("5", 0);
				qualHash.put(gender, value);
			}
			int num = qualHash.get(gender).get(rating);
			qualHash.get(gender).put(rating, num + 1);
		}

		//DIFFICULTY
		for(int i = 0; i < parser.data.size(); i++) {
			String gender = genders[i];
			String rating = difficulties[i];
			rating = Integer.toString((int) Double.parseDouble(rating));
			//System.out.println("Rat " + rating);

			if(diffHash.get(gender) == null){
				MyHashTable<String, Integer> value = new MyHashTable<>();
				value.put("1", 0);
				value.put("2", 0);
				value.put("3", 0);
				value.put("4", 0);
				value.put("5", 0);
				diffHash.put(gender, value);
			}
			int num = diffHash.get(gender).get(rating);
			diffHash.get(gender).put(rating, num + 1);
			//System.out.println("num " + num);
		}

		this.master.put("difficulty", diffHash);
		this.master.put("quality", qualHash);
	}

}
