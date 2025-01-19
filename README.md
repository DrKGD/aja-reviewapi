# ReviewAPI
ReviewAPI è meglio descritta come un sistema di Valutazione per Prodotti commerciali,
il progetto è una `proof-of-concept` sviluppato per il corso `Academy Java Angular`.

## Struttura del Progetto

Il progetto è suddiviso in tre principali componenti:

1. **Backend**: Sviluppato in Java, Gestisce la logica di business del server e si interfaccia con il database.
2. **Frontend**: Sviluppato in AngularJS, Interfaccia l'utente con il backend
3. **Slides**: Contiene il materiale di presentazione del progetto.

## Struttura delle Cartelle

```plaintext
/
├── backend/        # Codice sorgente del backend
├── frontend/       # Codice sorgente del frontend
├── slides/         # Materiale di presentazione del progetto
├── docker/         # Configurazioni preliminari di docker - mount
├── libs/           # Librerie utilizzate
├── .env            # Configurazione del progetto, variabili d'ambiente
├── LICENSE         # Licenza del progetto
└── README.md       # Questo file
```

## Building & Deployment
Per buildare le varie componenti del progetto è sufficiente richiamare le varie funzionalità presenti
nel file `mise.toml` tramite il software [Mise](https://github.com/jdx/mise).

```bash
mise task run sync
mise task run build-backend
mise task run build-frontend
```

Infine sarà sufficiente inizializzare docker con `docker compose up`, è possibile
configurare le proprietà di esecuzione del progetto andando ad aggiornare il file .env

# Licenza
Questo progetto è distribuito sotto licenza GNU. Vedi il file LICENSE per i dettagli.
Ci tengo a sottolineare che il prodotto non è e non sarà usato a scopi commerciali.

