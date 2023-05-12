package finalproject;


import java.util.ArrayList;

public class GenderByKeyword extends DataAnalyzer {

	private MyHashTable<String, MyHashTable<String, Integer>> master;
	private MyHashTable<String, Integer> table;

	public GenderByKeyword(Parser p) {
		super(p);
	}

	@Override
	public MyHashTable<String, Integer> getDistByKeyword(String keyword) {

		this.table = this.master.get(keyword);

		/*
		System.out.println(this.table.get("F"));
		System.out.println(this.table.get("X"));
		System.out.println(this.table.get("M"));
		 */

		return this.table;
	}

	@Override
	public void extractInformation() {

		// Set Up //
		this.master = new MyHashTable<>();
		this.table = new MyHashTable<String, Integer>();

		MyHashTable<String, Integer> visited = new MyHashTable<String, Integer>();

		int cIndex = parser.fields.get("comments");
		int gIndex = parser.fields.get("gender");

		String[] comments = new String[parser.data.size()];
		String[] genders = new String[parser.data.size()];

		for(int j = 0; j < parser.data.size(); j++){
			comments[j] = parser.data.get(j)[cIndex];
			genders[j] = parser.data.get(j)[gIndex];
		}

		// Call functions //
		//intoArray(comments);

		// Do it //
		for(int i = 0; i < parser.data.size(); i++) {
			visited = new MyHashTable<>();
			String row = comments[i].toLowerCase().
					replaceAll("[^a-z' ]"," ").trim();
			//String row = row0 + (" ");
			String gender = genders[i];

			for(String word: row.split( " ")){

				if(word.equals("")){
					continue;
				}

				if(master.get(word) == null){
					MyHashTable<String, Integer> value = new MyHashTable<>();

					value.put("M", 0);
					value.put("F", 0);
					value.put("X", 0);

					master.put(word, value);
				}
				if(visited.get(word) == null){
					visited.put(word, 0);
				}

				if(visited.get(word) == 0){
					int num = master.get(word).get(gender);
					master.get(word).put(gender, num + 1);
					visited.put(word, 1);
				}

			}
		}

	}


	/*
	private void intoArray(String[] comments){

		for(int i = 0; i < comments.length; i++) {
			String row0 = comments[i].toLowerCase().
					replaceAll("[^a-z']"," ");
			String row = row0 + (" ");
			commentSep.add(row.split(" "));
		}

	}

	 */

	/*
	private void intoUniqueArray(){

		for(int i = 0; i < commentSep.size(); i++) {
			ArrayList<String> temp = new ArrayList<String>(1);

			for(String w: commentSep.get(i)){
				if(w)
			}

		}

	}

	private void getWord(String keyword, gender){

		// Set Up //
		// //

		if(master.get(keyword) == null){
			table.put("M", 0);
			table.put("F", 0);
			table.put("X", 0);
		}

		for(int i = 0; i < parser.data.size(); i++){
			String row0 = comments[i].toLowerCase().
					replaceAll("[^a-z']"," ");
			String row1 = row0 + (" ");
			//String
			String gender = genders[i];


			if(row.contains("amazing") && gender.equals("M")){
				//System.out.println(row);
			}

			if(row.contains(keyword + " ")){
				int num = table.get(gender);
				//System.out.println(num);
				table.put(gender, num + 1);
			}

		}

	}

	 */

}
