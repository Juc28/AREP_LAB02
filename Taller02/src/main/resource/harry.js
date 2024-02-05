// Función para mostrar un mensaje de alerta con una frase de Harry Potter
function mensajeHarryPotter() {
  const frases = [
    "¡Expelliarmus!",
    "¡Wingardium Leviosa!",
    "¡Lumos!",
    "¡Nox!",
    "¡Accio!",
    "¡Diffindo!",
    "¡Petrificus Totalus!",
    "¡Obliviate!",
    "¡Riddikulus!",
    "¡Expecto Patronum!",
  ];

  const fraseAleatoria = frases[Math.floor(Math.random() * frases.length)];

  alert(fraseAleatoria);
}

// Función para cambiar el color de fondo de la página web según la casa de Hogwarts a la que perteneces
function cambiarColorFondoCasaHogwarts() {
  const casa = prompt("¿A qué casa de Hogwarts perteneces? (Gryffindor, Slytherin, Hufflepuff, Ravenclaw)");

  if (casa === "Gryffindor") {
    document.body.style.backgroundColor = "#b71c1c";
  } else if (casa === "Slytherin") {
    document.body.style.backgroundColor = "#008080";
  } else if (casa === "Hufflepuff") {
    document.body.style.backgroundColor = "#ffbf00";
  } else if (casa === "Ravenclaw") {
    document.body.style.backgroundColor = "#0073cf";
  } else {
    alert("No has introducido una casa válida.");
  }
}

// Función para mostrar una imagen aleatoria de un personaje de Harry Potter
function mostrarImagenPersonajeHarryPotter() {
  const personajes = [
    "harry-potter.jpg",
    "hermione-granger.jpg",
    "ron-weasley.jpg",
    "albus-dumbledore.jpg",
    "severus-snape.jpg",
    "lord-voldemort.jpg",
    "draco-malfoy.jpg",
    "bellatrix-lestrange.jpg",
    "rubeus-hagrid.jpg",
    "minerva-mcgonagall.jpg",
  ];

  const imagenAleatoria = personajes[Math.floor(Math.random() * personajes.length)];

  const imagen = document.createElement("img");
  imagen.src = `imagenes/${imagenAleatoria}`;
  imagen.style.width = "200px";
  imagen.style.height = "200px";

  document.body.appendChild(imagen);
}

// Llamadas a las funciones
mensajeHarryPotter();
cambiarColorFondoCasaHogwarts();
mostrarImagenPersonajeHarryPotter();
