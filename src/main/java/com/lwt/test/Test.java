package com.lwt.test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.log4j.Logger;

public class Test {
	private static Logger logger = Logger.getLogger(Test.class);
	private static final char char_null = (char)0;
	
	public static void main(String[] args) throws Exception{
		String szString = "this is an example of a huffman tree";
		char[] achCharArr = szString.toCharArray();
		
		Set<Unit> set_Units = new LinkedHashSet<Unit>();
		
		//去重
		for(char chChar: achCharArr){
			int icount = countChar(achCharArr, chChar);
			Unit unit = new Unit(chChar, icount);
			set_Units.add(unit);
		} 
		logger.debug(set_Units);
		System.out.println();
		
		//排序
		Unit[] aUnit = set_Units.toArray(new Unit[0]);
		Arrays.sort(aUnit, new Comparator<Unit>(){
			@Override
			public int compare(Unit u1, Unit u2) {
				return u1.iCount - u2.iCount;
			}
		});
		for(Unit unit : aUnit){
			logger.debug(unit);
		}
		System.out.println();
		
		//反转
		Unit unit_t;
		for(int i=0; i<(aUnit.length/2); i++){
			unit_t = aUnit[i];
			aUnit[i] = aUnit[aUnit.length-1-i];
			aUnit[aUnit.length-i-1] = unit_t;
		}
		for(Unit unit : aUnit){
			logger.debug(unit);
		}
		System.out.println();
		
		while(aUnit.length > 1){
			Unit unit_last1 = aUnit[aUnit.length-1];
			Unit unit_last2 = aUnit[aUnit.length-2];
			int iCount = unit_last1.getICount() + unit_last2.getICount();
			aUnit[aUnit.length-2] = new Unit(char_null, iCount, unit_last1, unit_last2);
			aUnit = Arrays.copyOf(aUnit, aUnit.length-1);
			Arrays.sort(aUnit, new Comparator<Unit>(){
				@Override
				public int compare(Unit u1, Unit u2) {
					return u2.iCount - u1.iCount;
				}
			});
			for(Object o : aUnit){
				logger.debug(o);
			}
			System.out.println();
		}
		
		Unit unit_tree = (Unit)aUnit[0];
		HashMap<Character, String> map = reveiwTree(unit_tree);
		logger.debug(map);		//此处得到huffman压缩对应表
		
		String value = "";
		for(char chChar : szString.toCharArray()){
			value += map.get(chChar);
		}
		logger.debug(value); //此处得到字符串的huffman表示形式
		
//		Byte byte_c = new Byte(value);
	}
	
	public static int countChar(char[] ach, char chChar){
		int count = 0;
		for(char ch : ach){
			if(ch == chChar){
				count++;
			}
		}
		return count;
	}
	
	public static HashMap<Character, String> reveiwTree(Unit tree){
		HashMap<Character, String> res = new HashMap<Character, String>();
		
		Unit left = tree.getUnit_left();
		Unit right = tree.getUnit_right();
		char left_char = left.getChChar();
		char right_char = right.getChChar();
		
		if(left_char != char_null){
			res.put(left_char, "0");
		}else{
			res = reveiwTree(left);
			for(Entry<Character, String> entry : res.entrySet()){
				entry.setValue("0" + entry.getValue());
			}
		}
		
		if(right_char != char_null){
			res.put(right_char, "1");
		}else{
			HashMap<Character, String> map = reveiwTree(right);
			for(Entry<Character, String> entry : map.entrySet()){
				entry.setValue("1" + entry.getValue());
			}
			res.putAll(map);
		}
		
		return res;
	}
	
	static class Unit implements Comparable<Unit>{
		private int iCount;
		private char chChar;
		private Unit unit_left;
		private Unit unit_right;
		public Unit getUnit_left() {
			return unit_left;
		}
		public Unit getUnit_right() {
			return unit_right;
		}
		public int getICount() {
			return iCount;
		}
		public char getChChar() {
			return chChar;
		}
		
		public Unit(char chChar, int iCount){
			this.iCount = iCount;
			this.chChar = chChar;
			this.unit_left = null;
			this.unit_right = null;
		}
		public Unit(char chChar, int iCount, Unit left, Unit right){
			this.iCount = iCount;
			this.chChar = chChar;
			this.unit_left = left;
			this.unit_right = right;
		}
		
		@Override
		public int compareTo(Unit o) {
			return iCount - o.iCount;
		}
		
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + chChar;
			result = prime * result + iCount;
			return result;
		}
		
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Unit other = (Unit) obj;
			if (chChar != other.chChar)
				return false;
			if (iCount != other.iCount)
				return false;
			return true;
		}
		
		@Override
		public String toString() {
			return "Unit [iCount=" + iCount + ", chChar=" + chChar
					+ ", unit_left=" + unit_left + ", unit_right=" + unit_right
					+ "]";
		}
	}
	
	
	
}
