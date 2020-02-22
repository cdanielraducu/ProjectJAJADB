

create table Locatii
(Locatie_ID int constraint pk_locatie PRIMARY KEY,
Cartier varchar(20) not null,
Strada varchar(20) not null,
Adresa varchar(20),
Utilizator varchar(20) not null constraint fk_utilizator references Utilizatori(Utilizator) on delete cascade,
constraint locatie_check check(Locatie_ID > 0 ));

create table Utilizatori
(Utilizator varchar(20) constraint pk_utilizator PRIMARY KEY,
Parola varchar(20) not null,
Nume varchar(20) not null,
Prenume varchar(20),
Email varchar(20),
Telefon varchar(10) not null,
constraint telefon_check check ([telefon] like '[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]'));

create table Livratori
(Livrator_ID int constraint pk_livrator PRIMARY KEY,
Nume varchar(30) not null,
Data_nasterii datetime,
Data_angajarii datetime default getdate());


create table Comenzi
( Comanda_ID int  constraint pk_comanda PRIMARY KEY,
Utilizator varchar(20) not null constraint fk_utilizator_id FOREIGN KEY references Utilizatori(Utilizator) on delete cascade,
Cost int not null,
Livrator_ID int not null constraint fk_livrator FOREIGN KEY references Livratori(Livrator_ID),
Data_comenzii datetime default getdate());

create table Pizza
( Pizza_ID int constraint pk_pizza PRIMARY KEY,
Nume varchar(20) not null,
Pret int not null,
Origine varchar(20));

create table IstoricComenzi
( Comanda_ID int,
Pizza_ID int,
constraint fk1_comanda foreign key (Comanda_ID) references Comenzi(Comanda_ID),
constraint fk2_pizza foreign key (Pizza_ID) references Pizza(Pizza_ID),
constraint pk_comanda_pizza primary key(Comanda_ID, Pizza_ID));

create table Ingrediente
(Ingredient_ID int constraint pk_ingredient primary key,
Nume varchar(20) not null constraint nume_unique UNIQUE);

create table IngredientPizza
( Pizza_ID int,
Ingredient_ID int,
constraint fk1_pizza foreign key(Pizza_ID) references Pizza(Pizza_ID) on delete cascade,
constraint fk2_ingredient foreign key(Ingredient_ID) references Ingrediente(Ingredient_ID) on delete cascade,
constraint pk_pizza_ingredient primary key(Pizza_ID, Ingredient_ID));



select * from dbo.IngredientPizza;

delete from Utilizatori
where utilizator = 'dani';

