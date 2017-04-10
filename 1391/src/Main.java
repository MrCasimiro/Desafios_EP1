import java.util.Scanner;
import java.util.*;

public class Main {
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		
		while(sc.hasNext()) {
			HashMap<Integer, ArrayList<Integer>> hate = new HashMap<Integer, ArrayList<Integer>>();
			HashMap<Integer, Character> mission = new HashMap<Integer, Character>();
			int n = sc.nextInt(); //number of astrounats
			int m = sc.nextInt(); //number of hate pairs
			int[] ages = new int[n];
			
			sc.nextLine();
			int average = 0;
			for(int i =0; i < n; i++) {
				ages[i] = sc.nextInt();
				hate.put(ages[i], new ArrayList<Integer> ());
				mission.put(ages[i], ' ');
				sc.nextLine();
				average += ages[i];
			}
			
			average = average / n;
			
			while(m > 0) {
				int ast_index = sc.nextInt() -1;
				int ast_index2 = sc.nextInt() -1;
				ArrayList<Integer> ast = hate.get(ages[ast_index]);
				ArrayList<Integer> ast2 = hate.get(ages[ast_index2]);
				ast.add(ages[ast_index2]);
				ast2.add(ages[ast_index]);
				hate.put(ages[ast_index], ast);
				hate.put(ages[ast_index2], ast2);
				
				sc.nextLine();
				m--;
			}
			/*
			for(int i = 0; i < n; i++) {
				System.out.println(ages[i]);
			} */
			//System.out.println("Average: " + average);
			gaveMission(hate, mission, ages, average);
			
			n = sc.nextInt();
			m = sc.nextInt();
			
			if(n == 0 && m == 0)
				break;
		}
		
	}
	
	
	public static void gaveMission(HashMap<Integer,ArrayList<Integer>> hate, HashMap<Integer, Character> mission, int[] ages, int average) {
		
		for(int i = 0; i < ages.length; i++) {
			int age = ages[i];
			int mission_num = 0;
			ArrayList<Integer> i_hate = hate.get(age);
			Iterator<Integer> itr = i_hate.iterator();
			
			//System.out.print(age + " ");
			boolean c_mission = true;
			if(age < average) { //young
				
				boolean b_mission = true; //permission for this mission
				
				
				while(itr.hasNext()) {
					int age_hate = itr.next();
					//System.out.print(age_hate + " ");
					// verificar se essa pessoa já está em uma missão
					if(mission.get(age_hate) == 'B') {
						b_mission = false;
					} else if(mission.get(age_hate) == 'C') {
						c_mission = false;
					} 
				}
				
				if(b_mission) {
					mission_num = 2;
				} else if(c_mission) {
					mission_num = 3;
				}
				
			} else { //senior
				boolean a_mission = true; //permission for this mission
				
				
				while(itr.hasNext()) {
					int age_hate = itr.next();
					//System.out.print(age_hate + " ");
					// verificar se essa pessoa já está em uma missão
					if(mission.get(age_hate) == 'A') {
						a_mission = false;
					} else if(mission.get(age_hate) == 'C') {
						c_mission = false;
					}
				}
				
				if(a_mission) {
					mission_num = 1;
				} else if(c_mission) {
					mission_num = 3;
				}
			}
			//System.out.println();
			switch(mission_num) {
				case 1:
					System.out.println("A");
					mission.put(age, 'A');
					break;
				case 2: 
					System.out.println("B");
					mission.put(age, 'B');
					break;
				case 3:
					System.out.println("C");
					mission.put(age, 'C');
					break;
				default:
					System.out.println("No solution.");
						
			}
		}
		
	}
	
}
