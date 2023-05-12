package finalproject;


import java.util.ArrayList;

public class RatingDistributionBySchool extends DataAnalyzer {

	private MyHashTable<String, MyHashTable<String, Integer>> table;
	private MyHashTable<String, Integer> numRatings;
	private MyHashTable<String, Double> avgRatings;
	private String[] profs;
	private String[] ratings;

	public RatingDistributionBySchool(Parser p) {
		super(p);
	}

	@Override
	public MyHashTable<String, Integer> getDistByKeyword(String keyword) {

		return this.table.get(keyword);
	}

	@Override
	public void extractInformation() {

		//combine prof name with average rating
		//hashtable key: school name, value: hashtable of key:prof name w avg, value: amount of those ratings

		// use same code from RDBP to get hashtables of ratings?
		// then use that to find the avg/most


		// Set Up //
		this.table = new MyHashTable<String, MyHashTable<String, Integer>>();

		int sIndex = parser.fields.get("student_star");
		int pIndex = parser.fields.get("professor_name");
		int lIndex = parser.fields.get("school_name");

		this.ratings = new String[parser.data.size()];
		this.profs = new String[parser.data.size()];
		String[] schools = new String[parser.data.size()];

		for(int j = 0; j < parser.data.size(); j++){
			ratings[j] = parser.data.get(j)[sIndex];
			profs[j] = parser.data.get(j)[pIndex];
			//System.out.println(profs[j]);
			schools[j] = parser.data.get(j)[lIndex];
		}

		getNumRatings();
		getAvgs();

		//System.out.println(this.numRatings);
		//System.out.println(this.numRatings.getEntries().get(0).getKey() + this.numRatings.getEntries().get(0).getValue());
		//System.out.println(this.avgRatings);
		//System.out.println(this.avgRatings.getEntries().get(0).getKey() + this.avgRatings.getEntries().get(0).getValue());

		// Main hashtable //

		for(int i = 0; i < parser.data.size(); i++){
			String name = this.profs[i].trim().toLowerCase();
			String school = schools[i].toLowerCase().trim();
			double average = this.avgRatings.get(name);
			int amountRate = this.numRatings.get(name);

			String label = name + "\n" + average;

			/*
			if(name.equals("amber  donahue")){
				System.out.println(average);
				System.out.println(amountRate);
				System.out.println(label);
			}
			 */

			if(this.table.get(school) == null){
				MyHashTable<String, Integer> value = new MyHashTable<>();
				this.table.put(school, value);
			}
			if(this.table.get(school).get(label) == null){
				int value = 0;
				this.table.get(school).put(label, value);
			}
			this.table.get(school).put(label, amountRate);
		}
	}

	private void getNumRatings(){
		this.numRatings = new MyHashTable<String, Integer>();
		//int counter = 0;

		for(int i = 0; i < parser.data.size(); i++){
			String name = this.profs[i].trim().toLowerCase();
			//double num =  Double.parseDouble(this.ratings[i]);

			if(numRatings.get(name) == null){
				int value = 0;
				numRatings.put(name, value);
			}

			int value = 0;
			int counter = numRatings.put(name, value);
			numRatings.put(name, counter + 1);
		}
	}

	private void getAvgs(){
		//hash of ratings
		MyHashTable<String, ArrayList<Double>> ratPerProf = new MyHashTable<>();

		for(int i = 0; i < parser.data.size(); i++){
			String name = this.profs[i].trim().toLowerCase();
			double num =  Double.parseDouble(this.ratings[i]);

			if(ratPerProf.get(name) == null){
				ArrayList<Double> value = new ArrayList<Double>(this.numRatings.get(name));
				ratPerProf.put(name, value);
			}
			ratPerProf.get(name).add(num);
		}

		//hash of avg
		this.avgRatings = new MyHashTable<>();

		for(int i = 0; i < ratPerProf.getEntries().size(); i++){
			//String name = this.profs[i].trim().toLowerCase();
			String name = ratPerProf.getEntries().get(i).getKey();
			Double avg = 0.0;

			for(int j = 0; j < ratPerProf.get(name).size(); j++){
				avg = avg + ratPerProf.get(name).get(j);
			}
			avg = avg/this.numRatings.get(name);
			avg = (double) Math.round(avg * 100)/100;

			if(avgRatings.get(name) == null){
				this.avgRatings.put(name, 0.0);
			}

			this.avgRatings.put(name, avg);


		}
	}



}
