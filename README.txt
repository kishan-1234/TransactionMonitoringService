Steps to run:

1. Clone this repo
2. Download maven dependencies specified in pom.xml file -> run  this command  - $ mvn clean install
3. Run Main class located inside src/main/java/org/example/

Overview:

1. FileReader class reads line by line from json file
2. For reach line read in step 1, TransactionProcessingService processes the transaction in a new thread
3. AccountTransactionRepository class holds all info related to account's monthly & daily transactions
4. TransactionRuleEvaluation service checks Rule 1 & Rule 2 in seperate threads immediately once the transaction is processed
5. If any rule is triggered, an event is publish to corresponding rule monitoring client. (Note that this is implemented via Observer pattern, clients(rule monitors) act as observers and TransactionRulesEvaluationService publish triggered rules to the clients)