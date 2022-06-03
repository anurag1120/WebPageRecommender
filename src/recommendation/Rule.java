package recommendation;

import java.util.ArrayList;


public class Rule{
    private ArrayList<Integer> ruleLeft;
    private ArrayList<Integer> ruleRight;
    private Integer support;
    private Double conf;
    public Rule() {
    }
    public Rule(ArrayList<Integer> ruleLeft, ArrayList<Integer> ruleRight, Integer support, Double conf) {
        this.ruleLeft = ruleLeft;
        this.ruleRight = ruleRight;
        this.support = support;
        this.conf = conf;
    }
    public ArrayList<Integer> getRuleLeft() {
        return ruleLeft;
    }
    public Double getConf() {
        return conf;
    }
    public void setConf(Double conf) {
        this.conf = conf;
    }
    public Integer getSupport() {
        return support;
    }
    public void setSupport(Integer support) {
        this.support = support;
    }
    public ArrayList<Integer> getRuleRight() {
        return ruleRight;
    }
    public void setRuleRight(ArrayList<Integer> ruleRight) {
        this.ruleRight = ruleRight;
    }
    public void setRuleLeft(ArrayList<Integer> ruleLeft) {
        this.ruleLeft = ruleLeft;
    }
    @Override
    public String toString() {
      String output = "[";
      for(int i=0;i<this.ruleLeft.size();i++){
          output= output+ ruleLeft.get(i);
          if(i!=this.ruleLeft.size()-1){
              output+=",";
          }
      }
      output+="] => [";
      for(int i=0;i<this.ruleRight.size();i++){
        output= output+ ruleRight.get(i);
        if(i!=this.ruleRight.size()-1){
            output+=",";
        }
      }
      output+="]  #support "+this.support;
      output+=" #confidence "+this.conf;
      return output;
    }
    
}