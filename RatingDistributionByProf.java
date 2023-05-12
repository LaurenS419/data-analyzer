package finalproject;


public class RatingDistributionByProf extends DataAnalyzer {

	private MyHashTable<String, MyHashTable<String, Integer>> profList;
	//private MyHashTable<String, Integer> table;

    public RatingDistributionByProf(Parser p) {
        super(p);
    }

	@Override
	public MyHashTable<String, Integer> getDistByKeyword(String keyword) {

		return profList.get(keyword);
	}

	@Override
	public void extractInformation() {
		// want names and their ratings
		// ratings are rounded down to nearest whole number
		// names are to be lower and no leading/trailing whitespace
		// multiple keys can map to the same value, key: rating, value: name
		// go through ratings, and make a new pair for each entry


		//hash table of profs - key: name value: new hashtable
		//new hashtable is key: "number" rating value: int of frequency

		//getProfs();
		this.profList = new MyHashTable<String, MyHashTable<String, Integer>>();

		int sIndex = parser.fields.get("student_star");
		//String[] ratings = parser.data.get(sIndex);
		int pIndex = parser.fields.get("professor_name");
		//String[] profs = parser.data.get(pIndex);

		String[] ratings = new String[parser.data.size()];
		String[] profs = new String[parser.data.size()];
		//System.out.println(parser.data.size());

		for(int j = 0; j < parser.data.size(); j++){
			ratings[j] = parser.data.get(j)[sIndex];
			profs[j] = parser.data.get(j)[pIndex];
		}

		for(int i = 0; i < parser.data.size(); i++){
			String rating = ratings[i];
			String name = profs[i].trim().toLowerCase();

			//System.out.println(rating + " " + name);

			rating = Integer.toString((int) Double.parseDouble(rating));

			if(profList.get(name) == null){
				MyHashTable<String, Integer> value = new MyHashTable<String, Integer>();
				value.put("1", 0);
				value.put("2", 0);
				value.put("3", 0);
				value.put("4", 0);
				value.put("5", 0);

				this.profList.put(name, value);
			}

			int oldRate = profList.get(name).put(rating, 0);
			//System.out.println(name + " " + rating + " " + profList.get(name).get(rating) + " " + oldRate);
			profList.get(name).put(rating, oldRate + 1);
			//System.out.println(profList.get(name).get(rating));


		}

	}


}
