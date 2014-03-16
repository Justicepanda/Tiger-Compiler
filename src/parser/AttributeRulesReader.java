package parser;

import java.util.ArrayList;
import java.util.List;

import utilities.ResourceFileScraper;

public class AttributeRulesReader 
{
	private final ResourceFileScraper scraper;

	  public AttributeRulesReader() {
	    scraper = new ResourceFileScraper();
	  }

	  public List<AttributeRule> determineFrom(String filename) {
	    String[] lines = scraper.read(filename);
	    List<AttributeRule> rules = new ArrayList<AttributeRule>();
	    for (String line: lines)
	      rules.add(AttributeRule.determineFrom(line));
	    return rules;
	  }
}
