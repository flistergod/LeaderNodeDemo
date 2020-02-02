# SD
Trabalho Final
Este Projeto utiliza como servidor para o web Service o Glassfish.
Este utiliza 2 ports: 4848 e 8080.
Caso estas estejam ocupadas, executa-se estes comandos no cmd:
netstat -ano | find ":8080" e netstat -ano | find ":4848"
depois de cada netstat executa-se taskkill /f /pid PROCESSO
sendo o PROCESSO, o PID a usar as ports

Neste projeto usamos uma biblioteca externa - javax 2.3 - war exploded para trabalhar com variáveis provenientes do xhtml

Neste projeto existem 3 grupos de multicast:
1º é para a comunicação entre PMs
2º é para o WEB service enviar pedidos ao líder.
3º é para o líder enviar respostas ao web Service

Cada mensagem proveniente do multicast tem um identificador (exemplo:"INFO:")
Tendo em conta o ID, trata-se dos valores proveninentes da mensagem
Existem no 1º grupo 5 IDs (INFO, ELECTION, ELECTOR, LEADER, INSERT)
e no 2º 3 IDs (CREATE, SEARCH, INSERT)

Identificadores e o que "fazem":
INFO - Partilha a port do PM aos outros PM
ELECTION - Informa uma ataulização de PMs e inicia a eleição do líder
ELECTOR - PM elege um líder
LEADER - É eleito o líder e informa-se os PM

CREATE - cria PM
SEARCH - mostra informações de PM
INSERT - insere nos PM uma nova localização

Criação de PM é feito no RMIRéplicaManager onde é criado um place manager, criado um registry e feito o bind

O programa começa no RMIClient inicializando no mínimo um PM 







