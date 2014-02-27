package parser;

import utilities.ResourceFileScraper;

import java.util.ArrayList;
import java.util.List;

public class GrammarRulesReader {
  private ResourceFileScraper scraper;
  private List<Rule> rules;

  public GrammarRulesReader() {
    scraper = new ResourceFileScraper();
  }

  public List<Rule> determineFrom(String filename) {
    String[] lines = scraper.read(filename);
    rules = new ArrayList<Rule>();
    for (String line: lines) {
      rules.add(Rule.determineFrom(line));
    }
    return rules;
  }
}
