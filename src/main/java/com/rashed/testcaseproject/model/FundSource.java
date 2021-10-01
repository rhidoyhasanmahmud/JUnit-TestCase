package com.rashed.testcaseproject.model;

public enum FundSource {
	/** ঋণ */
	DEBT("ঋণ"),
	/** অনুদান */
	GRANTS("অনুদান"),
	/** ইকুইটি */
	EQUITY("ইকুইটি"),
	/** অন্যান্য */
	OTHER("অন্যান্য");

	/** Title in Bangla */
	private String title;

	private FundSource(String title) {
		this.title = title;
	}

	public String title() {
		return this.title;
	}
}
