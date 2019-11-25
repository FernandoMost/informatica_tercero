package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.teatro.Funcion;
import sample.teatro.Obra;
import sample.teatro.Teatro;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

public class Main extends Application {
    public Teatro miTeatro;

    public Teatro getMiTeatro() {
        return miTeatro;
    }
    public void setMiTeatro(Teatro miTeatro) {
        this.miTeatro = miTeatro;
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));

        primaryStage.setTitle("Quiosco teatro");

        primaryStage.setScene(new Scene(root, 700, 1000));

        primaryStage.setResizable(false);

        primaryStage.show();
    }

    public static void main(String[] args) {
        Teatro miTeatro = new Teatro("Teatro municipal");

        new Obra(miTeatro, "Hamlet",                          "Escrita por William Shakespeare entre 1599 y 1601, Hamlet nos cuenta la historia del príncipe de Dinamarca, el cual enloquece al saber que su padre fue asesinado por su tío. La obra abarca temas como la venganza, la traición, la locura e incluso el incesto, siendo el trabajo más extenso de Shakespeare conocido. Debido a su fama, ha sido adaptada no solo al teatro sino también al cine y la televisión.");
        new Obra(miTeatro, "Romeo y Julieta",                 "Esta es otra de las obras clásicas de Shakespeare y probablemente la historia romántica más famosa en el mundo. Publicada originalmente con el nombre de «La más excelente y lamentable tragedia de Romeo y Julieta», esta nos cuenta el enamoramiento de sus dos protagonistas, pertenecientes a familias enemigas, los Montesco y los Capuleto. El argumento se caracteriza por su final trágico, cuando los amantes mueren tras no poder enfrentarse al odio entre sus parientes.\nCon numerosas adaptaciones al cine y la televisión, Romeo y Julieta también ha incursionado en el género de los musicales. Uno de los más célebres es el francés «Romeo et Juliette» del 2002, protagonizado por el actor y cantante Damien Sergue.");
        new Obra(miTeatro, "Cats",                            "Esta obra musical es una de las más célebres de Broadway. Fue creada por el legendario Andrew Lloyd Weber, quien a su vez se basó en los poemas de T. S. Eliot, «El libro de los gatos habilidosos del viejo Possum». Relata la historia de los Jellicle Cats, un grupo de gatos callejeros que poco a poco se va presentando ante el público, en medio de impresionantes números musicales.");
        new Obra(miTeatro, "La vida es sueño",                "El escritor Pedro Calderón de la Barca dio a conocer esta obra en 1635, la cual habla sobre un príncipe desterrado de su reino por su propio padre, a causa de una profecía fatal. Lo que vuelve tan interesante a este puesta en escena, son los temas filosóficos y religiosos que componen su trama, abarcando tópicos de religiones tan variadas como el hinduismo, el budismo y las tradiciones judeocristianas, entre otras.");
        new Obra(miTeatro, "El Fantasma de la Ópera",         "Este es uno de los musicales más aclamados alrededor del mundo y está basado en la novela de Gaston Leroux, publicada en el año 1910. La historia nos presenta a un ser oscuro que intenta ganarse el amor de Christine Daaé, joven aspirante a cantante de ópera. Se la considera una obra brillante por mezclar elementos góticos con géneros como el terror, el drama y el romance.");
        new Obra(miTeatro, "Los Miserables",                  "Basada en el clásico francés de Víctor Hugo, Los Miserables trata sobre la vida de Jean Valjean, un hombre humilde que sale de prisión dispuesto a cambiar. Valjean conmueve a los espectadores al tratar de hacer el bien a todos los personajes que se cruzan en su camino, a la vez que Francia es sacudida por los cambios de la Revolución Francesa.");
        new Obra(miTeatro, "La Celestina",                    "Publicada originalmente con el nombre de la Tragicomedia de Calisto y Melibea, es una obra que escribió Fernando de Rojas mezclando los géneros de drama y novela. En ella conocemos a Calisto, hijo de un acaudalado mercader que se ha enamorado de una muchacha llamada Melibea. Para lograr conquistarla, Calisto forma una alianza con una mujer chismosa y alcahueta, que procurará unirlo con su amada.");
        new Obra(miTeatro, "Sueño de una noche de verano",    "William Shakespeare regresa al último puesto de la lista con este clásico de la literatura, escrita en torno al año 1595. En Sueño de una noche de verano presenciamos un enredo amoroso durante la boda de Teseo, el duque de Atenas e Hipólita, la reina de las Amazonas. El reino de las hadas se mezcla con el de los humanos, dando lugar a una divertida y romántica comedia.");
        new Obra(miTeatro, "La Divina Comedia",               "La adaptación teatral se basa en el poema del mismo nombre, por Dante Alighieri, que narra el viaje de su protagonista por el paraíso, el purgatorio y el infierno. Lo más interesante acerca de esta obra, son sus múltiples referencias a la religión, la filosofía, las matemáticas y la astronomía, entre otros conceptos que la votan de simbolismos diversos.");
        new Obra(miTeatro, "Casa de Muñecas",                 "En 1879 se estrenó esta obra escrita por Henrik Ibsen. Sigue de cerca la historia de Nora y Torvaldo, matrimonio felizmente casado y con tres hijos. Su vida, aparentemente perfecta, se ve amenazada por un secreto de Nora, quien cometió un crimen para salvarle la vida a su marido. Poco a poco, el espectador presencia como la unión entre ambos se va derrumbando hasta desembocar en un final amargo.");
        new Obra(miTeatro, "Don Juan Tenorio",                "Se trata de una de las obras más representativas del siglo XIX, escrita por José Zorrilla. cuenta la historia de don Juan, hombre libertino y seductor que hace una apuesta con su amigo Luis Mejía, provocando el escándalo de la sociedad. El guión abarca temas universales como el amor, el honor y la venganza.");
        new Obra(miTeatro, "La Casa de Bernarda Alba",        "Esta obra teatral de tres actos fue escrita por Federico García Lorca en 1936 y publicada nueve años después, convirtiéndose en un gran éxito. En ella conocemos a doña Bernarda Alba, rigurosa mujer que tras la muerte de su esposo, amarga la vida de sus cuatro hijas al obligarlas a guardar luto por ocho años.");

        Calendar cal = Calendar.getInstance(), calFuncion = Calendar.getInstance();
        Date ahoraMismo = new Date();
        cal.setTime(ahoraMismo);
        cal.add(Calendar.MONTH, 2);
        Date enDosMeses = cal.getTime();

        ArrayList<Integer> randomHours = new ArrayList<>();
        for (int i=17; i<=23; i++) randomHours.add(i);
        Collections.shuffle(randomHours);

        for (int i = 0; i < 12; i++) {
            for (int j = 0; j < 15; j++) {
                Date fechaRandom = new Date(ThreadLocalRandom.current().nextLong(ahoraMismo.getTime(), enDosMeses.getTime()));
                calFuncion.setTime(fechaRandom);

                Collections.shuffle(randomHours);

                for (int k = 0; k < 3; k++) {
                    calFuncion.set(Calendar.HOUR_OF_DAY, randomHours.get(k));
                    calFuncion.set(Calendar.MINUTE, k%2 * 30);

                    new Funcion(miTeatro.getObras().get(i), calFuncion.getTime(), 12.0, 8.0);
                }
            }
        }

        launch(args);
    }
}
