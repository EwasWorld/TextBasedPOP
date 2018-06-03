package GameEngine;

import parser.GameLoop;
import world.Direction;
import world.Player;
import world.Room;
import world.RoomObject;



public class StartingTheGame {
    public static void init() {
        Room room = new Room(
                "Haze", true,
                "The world is swirling. A vague orb of blue bobs in the air in front of you, close enough to touch. "
                        + "Flashes of colour dart across its surface like meteors through the sky, each appearing for"
                        + " only a few seconds.",
                ""
        );
        RoomObject orb = new RoomObject(
                "orb", new String[]{}, "As your fingers touch its surface you a feel scorching pain.",
                "As your fingers touch its surface you a feel scorching pain.", "",
                "Your imagination is filled with thoughts of what you might do with its power. It "
                        + "seems like you'd be able to do anything you could fathom if only you had it in your grasp.",
                true
        );
        room.addRoomObject(orb);
        System.out.println(Player.setPlayerLocation(room));

        GameLoop.addTrigger(new GameLoop.ConditionalTrigger() {
            @Override
            public boolean condition() {
                return !Player.getPlayerLocation().contains(orb);
            }


            @Override
            public void action() {
                Player.setPlayerClass(Player.PlayerClass.WIZARD);
                GameLoop.addTrigger(() -> System.out.println(
                        "The orb starts to slowly suck your hand in. Your arm up to your elbow is now stuck inside. "
                                + "The sensation changed to a bitter cold and your fingers begin numb."));
                GameLoop.addTrigger(() -> {
                    System.out.println("You continue to be swallowed by the orb. Just before your face breaches its "
                                               + "surface you hear a clattering. The harsh cold disappears in an "
                                               + "instant and your fingers thaw. You feel a gentle warmth on your arm"
                                               + " and become aware of yourself lying on the floor.");

                    System.out.println(Player.setPlayerLocation(createAllRoomsAndObjects()));
                });
            }
        });
    }


    private static Room createAllRoomsAndObjects() {
        Room bedroom = new Room(
                "Bedroom",
                "A small bedroom no more than three meters square. Behind you is a "
                        + "bed with the thin, worn blanket rustled from a restless night. At its foot is "
                        + "a bookcase with a few clothes and two books you haven't read in years. To your"
                        + " left is a door slightly ajar, through it you can hear a lively conversation. "
                        + "In front of you in one corner is a fireplace with a small fire burning within "
                        + "it. Next to the fire a small wooden stool sits on its side, lying just shy of "
                        + "the flame's reach. Within the fire is an old book, so old that the pages have "
                        + "all fallen out and are scattered amongst the flames. One page managed to avoid"
                        + " the flame entirely and drifts towards your face, landing gently in front of "
                        + "you.",
                ""
        );
        bedroom.addRoomObject(new RoomObject("Prayer to Helm", new String[]{"Helm"}, "It's a piece of paper.", "",
                                             "That's a bad idea but hell, what do I know?",
                                             "A calling to Helm, god of protection, asking for his protection.", false
        ));
        bedroom.addRoomObject(
                new RoomObject("Prayer to Ralishaz", new String[]{"Ralishaz"}, "It's a piece of paper.", "",
                               "That's a bad idea but hell, what do I know?",
                               "A calling to Ralishaz, god of ill luck and insanity, asking for his fortune to shine "
                                       + "upon those around you.",
                               false
                ));
        bedroom.addRoomObject(new RoomObject("Prayer to Belenus", new String[]{"Belenus"}, "It's a piece of paper.", "",
                                             "That's a bad idea but hell, what do I know?",
                                             "A calling to Belenus, god of sun, light, and warmth, asking for his "
                                                     + "radiance in this dark place.",
                                             false
        ));
        bedroom.addRoomObject(new RoomObject("Prayer to Bast", new String[]{"Bast"}, "It's a piece of paper.", "",
                                             "That's a bad idea but hell, what do I know?",
                                             "A calling to Bast, goddess of cats and vengeance, asking for her "
                                                     + "strength in the task you have before you.",
                                             false
        ));
        bedroom.addRoomObject(new RoomObject("Prayer to Balinor", new String[]{"Balinor"}, "It's a piece of paper.", "",
                                             "That's a bad idea but hell, what do I know?",
                                             "A calling to Balinor, god of beasts and the hunt, asking for a friend.",
                                             false
        ));


        Room livingRoom = new Room("Living Room", "You find yourself in a larger living room with two small couches "
                + "around a low coffee table. To your right is an open door through which you can see a small kitchen"
                + ". There are three other doors to the left of yours along the same wall all with doors shut. In "
                + "front of you is a door leading out onto the street. In the room are your housemates. On one sofa "
                + "is Gilbert Clements, a solidly built man supporting a glorious mustache. He wears a chain mail "
                + "armour and leaning against the sofa is a large battle axe. On the other sofa is Hattie Caldwell, a"
                + " slim, muscular woman wearing a dark green tunic with a bow slung over one shoulder. Standing by "
                + "the door is Morris Fulton, a lean man dressed only in loose trousers and light boots and carrying "
                + "a staff in his hand. Upon seeing you the two on the sofa stand up and Gilbert grabs his axe, \"I "
                + "was beginning to think you'd slept in or something. Let's go then.\" With that the three of them "
                + "head out into the street.", "");


        Room street = new Room("Street", "You're in a loud street. You see your friends weave through the crowd to "
                + "the east.", "");
        livingRoom.addBidirectionalExit(bedroom, Direction.SOUTH);
        livingRoom.addBidirectionalExit(street, Direction.NORTH);

        Room colosseum = new Room("Colosseum", "You head through the tunnel and out into the arena of the colosseum. "
                + "The crowd screams as your group enters. On the other side of the arena is another team of three "
                + "halflings and a dwarf. The halflings were completely covered with everything but their eyes "
                + "concealed by dark fabric, while in contrast the bright dwarf carried a set of bagpipes and had a "
                + "longsword sheathed at his side.\n"
                + "A voice booms from in front of an extravagant set of royal boxes in the crowd stands, \"Welcome to"
                + " the final of the 634th Laurel Tournament, hosted in Phauslean. From over 200 teams we are down to"
                + " just two fighting for the chance at joining the ranks of the Exalted Laurels, a prestigious title"
                + " given to the strongest warriors of all the lands. We have our very own ragtag team of warriors "
                + "who have surprised us all in their constant victories.\" Your friends raise their hands and smile "
                + "to the applause. \"Then we have the favourites from Unreath who reached the semi-finals two years "
                + "ago but are back with even more punch. This year's final is a capture the flag game where the "
                + "attacking team must grab the defending team's flag within 30 minutes. If any team is incapable of "
                + "continuing, they lose. Through their speed in the previous round the tournament favourites earnt "
                + "the right to choose between attacking and defending. They have chosen to defend.\n"
                + "\"Is everybody ready?\", the announcer asked. The spectators excitedly joined in his next cry, "
                + "\"Casters, prepare the stage!\" A group of 40 people in royal blue capes and matching pointy hats "
                + "marched out of a tunnel in single file and positioned themselves around the edge of the arena. "
                + "They began a harsh, powerful dance and chanted, calling to Chislev, goddess of nature. Plants "
                + "sprouted from the sandy floor of the arena, slowly growing until a thick forest covered the ground"
                + ". The casters' chant changed to a softer, calmer tone and from their cupped hands wisps of light "
                + "entered the forest. The wisps projected their view above the trees for the spectators to see.\n"
                + "A gnome comes out from the tunnel and hands you each a small stone covered in intricate carvings. "
                + "As you take yours, Morris says, \"Thanks.\" Your stone pulses with a faint light and you hear his "
                + "thanks echoed through it. The caster's voice could again be heard booming over the excited crowd, "
                + "\"Let the games begin.\"\n"
                + "As the game starts Hattie scales a tree and swings through the branches to the north. Gilbert "
                + "wanders west, \"I guess I'll start looking too.\" Morris silently follows after Gilbert.", "");
        street.addBidirectionalExit(colosseum, Direction.EAST);

        return bedroom;
    }
}
