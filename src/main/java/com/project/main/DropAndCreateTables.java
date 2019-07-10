package com.project.main;

public class DropAndCreateTables {

	public static void main(String[] args) throws Exception {

		DataBase.dropTables();
		DataBase.createTables();
		
		System.out.println("Drop tables successed");
		System.out.println("Create tables successed");

	}

}
