header {
	package by.bsuir.iit.kp.expert.parsers.gen;
}

{
	import java.util.*;
	import by.bsuir.iit.kp.expert.builders.*;
	import by.bsuir.iit.kp.expert.exceptions.*;
	import by.bsuir.iit.kp.expert.presentation.*;
	import by.bsuir.iit.kp.expert.presentation.base.*;	
}

/* -------------------- PARSER ----------------- */
class ANTLRModelParser extends ANTLRBaseModelParser;
options {
   defaultErrorHandler = false;
}
{
	private ModelBuilder builder = new ModelBuilder();
	private SymbolAttributeBuilder sBuilder = new SymbolAttributeBuilder();
}

model returns [Model model] throws ModelParserException
	: (attribute_description)+ (rule_description)+ (scenario_description)+
		{			
			model = builder.getModel();
		}
	;

attribute_description throws ModelParserException
	: number_attribute_description | symbol_attribute_description;

number_attribute_description throws ModelParserException
	{ String fr = null; String to = null; String def = null; String c = null; }
	: "number" "attribute" i:ID (c=quoted_str)? ("from" fr=val)? ("to" to=val)? ("default" def=val)?
		{
			double fromValue = 0.0;
			double toValue = 0.0;
			double defaultValue = 0.0;
			
			NumberAttribute attr = new NumberAttribute(i.getText());
			
			if (c != null) {
				attr.setDescription(c);
			}
		
			try {			
				if (fr != null) {
					fromValue = Double.parseDouble(fr);
				}
				if (to != null) {
					toValue = Double.parseDouble(to);
				}
				

				Constraints constraints = null;
				if (fr != null) {
					if (to != null) {
						constraints = new Constraints(fromValue,toValue);
					} else {
						constraints = new Constraints(true,fromValue);
					}
				} else {
					if (to != null) {
						constraints = new Constraints(false,toValue);
					} else {
						constraints = new Constraints();
					}
				}
				
				attr.setConstraints(constraints);
				
				if (def != null) {
					double defValue = Double.parseDouble(def);
					attr.setDefaultValue(defValue);
				}
				
				builder.addNumberAttribute(attr);
				//System.out.println(attr);	// debug
			} catch (ModelException e) {
				throw new ModelParserException(LT(1).getLine(),e.getMessage());
			}
		}
	;
		
symbol_attribute_description throws ModelParserException
	{ sBuilder.reset(); String c = null; }
	: "symbol" "attribute" i:ID (c=quoted_str)? (symbol_description)+
		{
			SymbolAttribute attr = sBuilder.getSymbolAttribute(i.getText());
			if (c != null) {
				attr.setDescription(c);
			}
		
			try {
				builder.addSymbolAttribute(attr);
			} catch (ModelException e) {
				throw new ModelParserException(LT(1).getLine(),e.getMessage());
			}
			//System.out.println(attr);	// debug
		}
	;
	
symbol_description returns [Symbol s] throws ModelParserException 
	{ String def = null; String c = null; }
	: "value" i:ID (c=quoted_str)? ("assertion" "name" aid:ID)? ("default" def=val)?
		{
			s = new Symbol(i.getText());
			if (c != null) {
				s.setDescription(c);
			}
			if (aid != null) {
				s.setAssertionName(aid.getText());
			}
			
			try {
				if (def != null) {
					double defaultValue = Double.parseDouble(def);
					s.setDefaultValue(defaultValue);
				}
				
				sBuilder.addSymbol(s);
			} catch (ModelException e) {
				throw new ModelParserException(LT(1).getLine(),e.getMessage());
			}	
		}
	;

rule_description throws ModelParserException
	{ String cond = null; String r; String c = ""; }
	: "rule" i:ID "target" r=ref (cond=applicability_condition)? (c=quoted_str)?
		{
			Rule rule = new Rule(i.getText());
			rule.setTarget(r);
			rule.setApplicabilityExpression(cond);
			if (c != null) {
				rule.setActionString(c);
			}
			
			try {
				builder.addRule(rule);
			} catch (ModelException e) {
				throw new ModelParserException(LT(1).getLine(),e.getMessage());
			}
			//System.out.println(rule);	// debug
		}
	;

scenario_description throws ModelParserException 
	{ ScenarioStatement op; String ac = null; }
	: i:INT_VALUE COLON op=operator (ac=applicability_condition)? SEMICOLON
		{
			op.setLabel(i.getText());
			op.setApplicabilityExpression(ac);
			
			try {
				builder.addScenarioStatement(op);
			} catch (ModelException e) {
				throw new ModelParserException(LT(1).getLine(),e.getMessage());
			}
			//System.out.println(op);	// debug
		}
	;
	
operator returns [ScenarioStatement op] throws ModelParserException 
	{ String argline = ""; }
	: i:ID (argline=arg_list)?
		{
			String opid = i.getText();
			op = new ScenarioStatement(opid,argline);
		}
	;
	
/* -------------------- LEXER ----------------- */
class ANTLRModelLexer extends ANTLRBaseModelLexer;
options {
	k = 2;
	caseSensitiveLiterals = false;
	charVocabulary = '\u0003'..'\u00FF';
	testLiterals = false;
	importVocab = ANTLRModelParser;
}

protected DUMMY: ;