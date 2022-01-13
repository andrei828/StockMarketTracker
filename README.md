# Aplicatie Web pentru vizualizat actiuni

Aplicatia ofera atat o interfata REST API cat si una vizuala. Scopul solutiei este sa ofere utilizatorului o modalitate prin care poate gestiona
un portofoliu de actiuni, sa isi administreze un cont, sa compare diferite actiuni si sa decida sa vada evolutia acestora in timp.
### Entitatile
- User
- Portfolio
- Stock
- StockValueDatePair
- Analyzer
- Wishlist

### Business requirements
- Utilizatorul isi poate crea un cont.
- Utilizatorul poate vizualiza o lista de actiuni.
- Utilizatorul poate gestiona un portofoliu de actiuni.
- Utilizatorul poate vedea un grafic cu evolutia unei actiuni in timp.
- Utilizatorul poate compara doua sau mai multe actiuni pentru a vedea diferenta de performanta in timp.
- Utilizatorul poate adauga in "Wishlist" actiunile care sunt in plan de cumparare dar nu sunt inca gata de a intra in portofoliu.

Aplicatia foloseste SWAGGER2 pentru a oferi mai multe informatii de cum se poate interactiona cu serviciul de REST. Pentru a putea folosi aplicatia, fiecare
utilizator trebuie sa se inregistreze. Parola este criptata in baza de date iar fiecare request este persistat de sesiune. 
