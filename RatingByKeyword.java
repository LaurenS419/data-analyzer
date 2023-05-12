package finalproject;


public class RatingByKeyword extends DataAnalyzer {

	private MyHashTable<String, MyHashTable<String, Integer>> master;
	private MyHashTable<String, Integer> table;

    public RatingByKeyword(Parser p) {
        super(p);
    }

	@Override
	public MyHashTable<String, Integer> getDistByKeyword(String keyword) {

		this.table = this.master.get(keyword);

		/*
		System.out.println(this.table.get("1"));
		System.out.println(this.table.get("2"));
		System.out.println(this.table.get("3"));
		System.out.println(this.table.get("4"));
		System.out.println(this.table.get("5"));
		 */

		return this.table;

	}

	@Override
	public void extractInformation() {

		this.master = new MyHashTable<>();
		this.table = new MyHashTable<String, Integer>();

		MyHashTable<String, Integer> visited = new MyHashTable<String, Integer>();

		// Set Up //
		int cIndex = parser.fields.get("comments");
		int sIndex = parser.fields.get("student_star");

		String[] comments = new String[parser.data.size()];
		String[] ratings = new String[parser.data.size()];

		for(int j = 0; j < parser.data.size(); j++){
			comments[j] = parser.data.get(j)[cIndex];
			ratings[j] = parser.data.get(j)[sIndex];
		}

		// Do it //
		for(int i = 0; i < parser.data.size(); i++) {
			visited = new MyHashTable<>();
			String row = comments[i].toLowerCase().
					replaceAll("[^a-z' ]"," ").trim();
			//String row = row0 + (" ");
			String rating = ratings[i];
			rating = Integer.toString((int) Double.parseDouble(rating));

			for(String word: row.split( " ")){

				if(word.equals("")){
					continue;
				}

				if(master.get(word) == null){
					MyHashTable<String, Integer> value = new MyHashTable<>();

					value.put("1", 0);
					value.put("2", 0);
					value.put("3", 0);
					value.put("4", 0);
					value.put("5", 0);

					master.put(word, value);
				}
				if(visited.get(word) == null){
					visited.put(word, 0);
				}

				if(visited.get(word) == 0){
					int num = master.get(word).get(rating);
					master.get(word).put(rating, num + 1);
					visited.put(word, 1);
				}

			}
		}

	}



}
