package parser;

import utilities.ResourceFileScraper;

import java.util.ArrayList;
import java.util.List;

class GrammarRulesReader {
  private final ResourceFileScraper scraper;

  public GrammarRulesReader() {
    scraper = new ResourceFileScraper();
  }

  public List<Rule> determineFrom(String filename) {
    String[] lines = scraper.read(filename);
    List<Rule> rules = new ArrayList<Rule>();
    for (String line: lines) {
      rules.add(Rule.determineFrom(line));
    }
    return rules;
  }
}
