import threading
import random

TOTAL_LISTAS = 100
TAMANHO_POR_LISTA = 1000
MIN_VAL = 1000
MAX_VAL = 100000

def popular_lista(lista, tamanho):
    t = threading.current_thread()
    print(f"Thread {t.name} | ID interno: {threading.get_ident()} | Param: {tamanho}")
    rnd = random.Random()
    for _ in range(tamanho):
        lista.append(rnd.randint(MIN_VAL, MAX_VAL))

def soma_e_conta(lista, idx, resultados):
    t = threading.current_thread()
    print(f"Thread {t.name} | ID interno: {threading.get_ident()} | Param: lista-{idx}")
    s = sum(lista)
    c = len(lista)
    resultados[idx] = (s, c)

if __name__ == "__main__":
    listas = [ [] for _ in range(TOTAL_LISTAS) ]

    threads_pop = []
    for i in range(TOTAL_LISTAS):
        thr = threading.Thread(target=popular_lista, args=(listas[i], TAMANHO_POR_LISTA), name=f"Popula-{i}")
        threads_pop.append(thr)
        thr.start()

    for thr in threads_pop:
        thr.join()
    resultados = [None] * TOTAL_LISTAS
    threads_calc = []
    for i in range(TOTAL_LISTAS):
        thr = threading.Thread(target=soma_e_conta, args=(listas[i], i, resultados), name=f"Soma-{i}")
        threads_calc.append(thr)
        thr.start()

    for thr in threads_calc:
        thr.join()
        
    total_soma = sum(r[0] for r in resultados)
    total_contagem = sum(r[1] for r in resultados)
    media_global = total_soma / total_contagem if total_contagem > 0 else 0.0

    print("\nResultado final:")
    print(f"Total elementos: {total_contagem}")
    print(f"Soma total: {total_soma}")
    print(f"Média global: {media_global:.6f}")
