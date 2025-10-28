import xmlrpc.client, datetime

# Conecta ao servidor RPC
servidor = xmlrpc.client.ServerProxy("http://10.104.12.12:1099/")

# --- CORREÇÃO AQUI ---
# Chamando o método 'hoje_eh', como registrado no servidor
hoje = servidor.hoje_eh() 

# O resto do seu código está correto para manipular a resposta
data_hora_convertida = datetime.datetime.strptime(hoje.value, "%Y%m%dT%H:%M:%S")
print("Hoje é: %s" % data_hora_convertida.strftime("%d.%m.%Y, %H:%M:%S"))

# As chamadas restantes já estavam corretas
nome = 'Alexandre de Oliveira Zamberlan'
email = servidor.gerar_email(nome)
print(f'Email: {email}')


frase = 'a turma de sistemas distribuídos da UFN é composta de alunos do sexo masculino. ' \
'Os alunos são divididos em grupos: os que moram foram de Santa Maria; os que jogam volei; e os ' \
'bagaços... que não fazem os exercícios que o profe querido pediu com carinho.'

frase_sem_artigos = servidor.retirar_artigos(frase)
print(f'Frase sem artigos: ', frase_sem_artigos)