package com.evaluation.services;

public class EvalObj {
	private Integer value;
	private Integer next;

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	public Integer getNext() {
		return next;
	}

	public void setNext(Integer next) {
		this.next = next;
	}

	public EvalObj(Integer value) {
		this.value = value;
		this.next = null;
	}
}
