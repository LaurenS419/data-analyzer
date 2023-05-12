package finalproject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

public class MyHashTable<K,V> implements Iterable<MyPair<K,V>>{

	private int size; // num of entries to the table
	private int capacity = 16; // num of buckets
	private static final double MAX_LOAD_FACTOR = 0.75; // load factor needed to check for rehashing
	private ArrayList<LinkedList<MyPair<K,V>>> buckets; // ArrayList of buckets. Each bucket is a LinkedList of HashPair


	// constructors
	public MyHashTable() {
		//System.out.println("default");
		this.size = 0;
		this.buckets = new ArrayList<LinkedList<MyPair<K,V>>>(this.capacity);

		for(int i = 0; i < this.capacity; i++){
			//MyPair pair = new MyPair(i, "");
			LinkedList<MyPair<K,V>> bucket = new LinkedList<>();
			//bucket.add(pair);

			this.buckets.add(i, bucket);
		}
	}

	public MyHashTable(int initialCapacity) {
		//System.out.println("init Cap " + initialCapacity);
		this.size = 0;
		this.capacity = initialCapacity;
		this.buckets = new ArrayList<LinkedList<MyPair<K,V>>>(initialCapacity);

		for(int i = 0; i < initialCapacity; i++){
			LinkedList<MyPair<K,V>> bucket = new LinkedList<>();

			this.buckets.add(i, bucket);
		}
	}

	public int size() {
		return this.size;
	}

	public boolean isEmpty() {
		return this.size == 0;
	}

	public int numBuckets() {
		return this.capacity;
	}

	/**
	 * Returns the buckets variable. Useful for testing  purposes.
	 */
	public ArrayList<LinkedList< MyPair<K,V> > > getBuckets(){
		return this.buckets;
	}

	/**
	 * Given a key, return the bucket position for the key. 
	 */
	public int hashFunction(K key) {
		int hashValue = Math.abs(key.hashCode())%this.capacity;
		return hashValue;
	}

	private double getLoadFactor(){
		return ((double) this.size)/((double) this.capacity);
	}

	/**
	 * Takes a key and a value as input and adds the corresponding HashPair
	 * to this HashTable. Expected average run time  O(1)
	 */
	public V put(K key, V value) {
		//System.out.println("put");
		int hValue = hashFunction(key);

		V temp = null;
		MyPair pair = new MyPair(key,value);
		int counter = 0;
		boolean changed = false;


		if(this.buckets.get(hValue).size() != 0 ) {
			counter = 0;
			for(MyPair p: this.buckets.get(hValue)){
				if(p.getKey().equals(key)){
					temp = (V) p.getValue();
					p.setValue(value);
					changed = true;
					//this.buckets.get(hValue).remove(counter);
				}
				counter++;
			}
		}

		if(!changed){
			this.size++;
			if(this.getLoadFactor() > MAX_LOAD_FACTOR) {
				this.rehash();
			}
			this.buckets.get(hashFunction(key)).add(pair);
		}

		return temp;
	}


	/**
	 * Get the value corresponding to key. Expected average runtime O(1)
	 */

	public V get(K key) {
		V temp = null;


		for(MyPair pair: this.buckets.get(hashFunction(key))){
			if(pair.getKey().equals(key)){
				temp = (V) pair.getValue();

			}

		}


		return temp;

	}

	/**
	 * Remove the HashPair corresponding to key . Expected average runtime O(1) 
	 */
	public V remove(K key) {
		V temp = null;
		int j = 0;

		for(MyPair pair: this.buckets.get(hashFunction(key))){
			if(pair.getKey().equals(key)){
				temp = (V) pair.getValue();
				this.buckets.get(hashFunction(key)).remove(j);
				j++;
			}
		}
		return temp;

	}


	/** 
	 * Method to double the size of the hashtable if load factor increases
	 * beyond MAX_LOAD_FACTOR.
	 * Made public for ease of testing.
	 * Expected average runtime is O(m), where m is the number of buckets
	 */
	public void rehash() {

		ArrayList<LinkedList<MyPair<K,V>>> temp = this.buckets;
		this.buckets = new ArrayList<LinkedList<MyPair<K,V>>>(this.capacity*2);

		for(int i = 0; i < this.capacity*2; i++){
			LinkedList<MyPair<K, V>> bucket = new LinkedList<>();
			this.buckets.add(i, bucket);
		}
		this.capacity = this.capacity*2;

		for(int i = 0; i < temp.size(); i++){
			if(temp.get(i).size() != 0 ){

				for(MyPair pair: temp.get(i)){
					//System.out.println(pair.getValue().toString());
					int newIndex = hashFunction((K) pair.getKey());
					this.buckets.get(newIndex).add(pair);


					//System.out.println("rehash");
					//System.out.println(this.buckets.get(newIndex).get(0).getValue());
				}


			}
		}

	}


	/**
	 * Return a list of all the keys present in this hashtable.
	 * Expected average runtime is O(m), where m is the number of buckets
	 */

	public ArrayList<K> getKeySet() {
		ArrayList<K> temp = new ArrayList<>(this.size);

		for(int i = 0; i < this.capacity; i++){
			if(this.buckets.get(i).size() != 0){
				for(MyPair pair: this.buckets.get(i)){
					temp.add((K) pair.getKey());
				}
			}
		}

		return temp;

	}

	/**
	 * Returns an ArrayList of unique values present in this hashtable.
	 * Expected average runtime is O(m) where m is the number of buckets
	 */
	public ArrayList<V> getValueSet() {
		ArrayList<V> temp = new ArrayList<>(this.size);
		ArrayList<V> uniques = new ArrayList<>(this.size);

		for(int i = 0; i < this.capacity; i++){
			if(this.buckets.get(i).size() != 0){
				for(MyPair pair: this.buckets.get(i)){
					temp.add((V) pair.getValue());
				}
			}
		}

		for(V value: temp){
			if(!uniques.contains(value)){
				uniques.add(value);
			}
		}

		return uniques;
	}


	/**
	 * Returns an ArrayList of all the key-value pairs present in this hashtable.
	 * Expected average runtime is O(m) where m is the number of buckets
	 */
	public ArrayList<MyPair<K, V>> getEntries() {
		ArrayList<MyPair<K, V>> temp = new ArrayList<>(this.size);

		for(int i = 0; i < this.capacity; i++){
			if(this.buckets.get(i).size() != 0){
				for(MyPair pair: this.buckets.get(i)){
					temp.add(pair);
				}
			}
		}

		return temp;
	}

	@Override
	public MyHashIterator iterator() {
		return new MyHashIterator();
	}   

	private class MyHashIterator implements Iterator<MyPair<K,V>> {
		private MyPair<K,V> current;
		private ArrayList<MyPair<K, V>> entries;
		private int curIndex;

		private MyHashIterator() {
			//this.entries = getBuckets();
			this.entries = getEntries();
			this.current = this.entries.get(0);
			this.curIndex = 0;
			//System.out.println(this.entries.get(curIndex).toString());
		}

		@Override
		public boolean hasNext() {
			//System.out.println(this.curIndex < this.entries.size() - 1);
			return this.curIndex < this.entries.size();
			//return this.current != null;
		}

		@Override
		public MyPair<K,V> next() {
			MyPair<K,V> temp = this.current;
			this.curIndex++;

			if(this.curIndex == this.entries.size()){
				//throw new NoSuchElementException();
				return temp;
			}

			this.current = entries.get(curIndex);
			return temp;

		}

	}

	/*
	public static void main(String[] args){
		MyHashTableADV<Integer, String> h = new MyHashTableADV<Integer, String>(6);

		h.put(3, "Hello");
		h.put(9, "World");
		h.put(15, "Goodbye");
		h.put(21, "World");

		System.out.println(h.getBuckets());

		h.put(1, "World");
		System.out.println(h.getBuckets());
	}
	 */
	
}


