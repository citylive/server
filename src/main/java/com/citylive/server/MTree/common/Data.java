package com.citylive.server.MTree.common;


import java.util.Arrays;

public class Data implements DistanceFunctions.EuclideanCoordinate, Comparable<Data> {
	String id;
	private final double[] values;
	private final int hashCode;

	public Data(String id, double... values) {
		this(values);
		this.id = id;
	}

	public Data(double... values) {
		this.values = values;
		int hashCode = 1;
		for(double value : values) {
			hashCode = 31*hashCode + (int)(value);
		}
		this.hashCode = hashCode;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public int dimensions() {
		return values.length;
	}

	@Override
	public double get(int index) {
		return values[index];
	}
	
	@Override
	public int hashCode() {
		return hashCode;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Data) {
			Data that = (Data) obj;
			if(this.dimensions() != that.dimensions()) {
				return false;
			}
			for(int i = 0; i < this.dimensions(); i++) {
				if(this.values[i] != that.values[i]) {
					return false;
				}
			}
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public int compareTo(Data that) {
		int dimensions = Math.min(this.dimensions(), that.dimensions());
		for(int i = 0; i < dimensions; i++) {
			double v1 = this.values[i];
			double v2 = that.values[i];
			if(v1 > v2) {
				return +1;
			}
			if(v1 < v2) {
				return -1;
			}
		}
		
		if(this.dimensions() > dimensions) {
			return +1;
		}
		
		if(that.dimensions() > dimensions) {
			return -1;
		}
		return this.id.compareTo(that.id);
	}

	@Override
	public String toString() {
		return "Data{" +
				"id='" + id + '\'' +
				", values=" + Arrays.toString(values) +
				", hashCode=" + hashCode +
				'}';
	}
}