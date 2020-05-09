class DSArrayListTester{

    public static void main(String[] args){
	DSArrayList dsal = new DSArrayList();

	System.out.println(dsal); // Call the DSArrayList toString() method
	dsal.add(5);
	dsal.add(3);
	System.out.println(dsal); // Call the DSArrayList toString() method
	dsal.add(1);
	dsal.add(2);
	dsal.add(412434);
	System.out.println(dsal); // Call the DSArrayList toString() method

	for(int i = 0; i < 1000000; i++){
	    dsal.add(i/13);
	}
    }

    

}
