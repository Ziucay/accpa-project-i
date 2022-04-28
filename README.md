# accpa-project-i

## How to use prototype
1. Clone repository to your machine
2. Go into src/main/java/Application
3. Run it with command line arguments: name of file to test with extension, identifier of function you want to run

## Language Syntax
Below is the planned syntax of the program, written in similar to the byacc parser language.
Before colon we have an entity – this is some part of the language, which is defined by text after the colon. As a basic entity of the language we have:
Keywords : type,function, import, auto, integer, double, boolean, string, array, dict, while, loop, end, for, in, reverse, if, then, else, and, or, xor, <, <=, ==, >=, >
special symbols ( *, /,  %, +, -, =)
literals (“some text”, 1, true, false)

On the right hand side, we have the declaration of entity. Parser takes tokens and matches them with these rules.

‘{...}’ means that anything inside of the curly braces can be repeated arbitrary times

‘... | … | …’ means that entity will match, if it corresponds to any of the entities in curly braces.

‘[...]’ means that this part can be optional

IntegralLiteral, StringLiteral, DoubleLiteral are numbers, strings and real numbers, correspondingly.



Program : { FunctionDeclaration | Import }

SimpleDeclaration : VariableDeclaration | TypeDeclaration

VariableDeclaration : Type Identifier = Expression

TypeDeclaration : type Identifier is Type

FunctionDeclaration : function Identifier ( Parameters ) [ : Type ] is Body end

Parameters : ParameterDeclaration [{  , ParameterDeclaration }]

ParameterDeclaration : Identifier : Identifier

Import : import StringLiteral

Type : PrimitiveType | ArrayType | DictionaryType | auto

PrimitiveType: int | double | boolean | string

ArrayType : array Type Identifier = [Expression]

DictionaryType: dict Type Type Identifier

DictionaryMember: Identifier[Expression]

ArrayMember  Identifier[Expression]

Body : { SimpleDeclaration | Statement }

Statement : Assignment | RoutineCall | WhileLoop | ForLoop | IfStatement

Assignment : Identifier = Expression | ArrayMember = Expression

FunctionCall : Identifier ([ Expression [{ , Expression } ] ])

WhileLoop : while Expression loop Body end

ForLoop : for Identifier Range loop Body end Range : in [ reverse ] Expression .. Expression

IfStatement : if Expression then Body [ else Body ] end

Expression : Relation [ and Relation ] | Relation [ or Relation ]  | Relation [ xor Relation ]

Relation : Simple [ < Simple ] | Simple [ <= Simple ] | Simple [ > Simple ] | Simple [ >= Simple ] |
Simple [ == Simple ] Simple [ /= Simple ]

Simple : Factor [ ( * | / | % ) Factor ] | Factor [ / Factor ] | Factor [ % Factor ]

Factor : Summand [ + Summand ] | Summand [ - Summand ]

Summand : Primary | ( Expression )

Primary : IntegralLiteral | StringLiteral | DoubleLiteral | true | false | ArrayMember

ReturnStatement: return Expression

PrintStatement : print Expression

## What we implemented so far

- Lexical analysis
- Parsing, building AST (partially)
- Interpreter (partially)
- variables(explicit typing)
- functions
- built-in print method
- int type support
- basic expression calculation

## What we are going to implement

- Type checking
- Type inference
- support for double, boolean, string
- Interactive mode of the interpreter
- conditional operations, loops
- user-defined types
- single-line comments
- simple imports
- standart library
- first-class functions
- built-in arrays
- built-in dicts

