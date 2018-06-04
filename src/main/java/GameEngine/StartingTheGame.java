package GameEngine;

import parser.Command;
import parser.GameLoop;
import parser.ParsedCommand;
import world.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;



public class StartingTheGame {
    public static void init() {
        final Room room = new Room(
                "Haze", true,
                "The world is swirling. A vague orb of blue bobs in the air in front of you, close enough to touch. "
                        + "Flashes of colour dart across its surface like meteors through the sky, each appearing for"
                        + " only a few seconds.",
                ""
        );
        // TODO: remove alternate name when other orbs are added
        room.addRoomObject(new TakableItem(
                "Blue orb", new String[]{"orb"}, "As your fingers touch its surface you a feel scorching pain.",
                "As your fingers touch its surface you a feel scorching pain.", "",
                "Your imagination is filled with thoughts of what you might do with its power. It "
                        + "seems like you'd be able to do anything you could fathom if only you had it in your grasp.",
                true
        ));
        System.out.println(Player.setPlayerLocation(room));

        // Orb chooser and warp player to bedroom
        GameLoop.addTrigger(new GameLoop.ConditionalTrigger() {
            @Override
            public boolean condition() {
                return !Player.getPlayerLocation().contains("orb");
            }


            @Override
            public void action() {
                final Player.PlayerClass playerClass = Player.PlayerClass.WIZARD;
                Player.setPlayerClass(playerClass);
                // Set up the bedroom based upon their class
                final Room bedroom = createWizardBedroom();

                if (Player.hasObject("blue orb")) {
                    Player.removeObject("blue orb");
                }

                GameLoop.addTrigger(() -> System.out.println(
                        "The orb starts to slowly suck your hand in. Your arm up to your elbow is now stuck inside. "
                                + "The sensation changed to a bitter cold and your fingers begin numb."));
                GameLoop.addTrigger(() -> {
                    System.out.println("You continue to be swallowed by the orb. Just before your face breaches its "
                                               + "surface you hear a clattering. The harsh cold disappears in an "
                                               + "instant and your fingers thaw. You feel a gentle warmth on your arm"
                                               + " and become aware of yourself lying on the floor.");

                    // Set up the next room based on their class
                    System.out.println(Player.setPlayerLocation(createOtherRoomsAndObjects(bedroom)));
                });
            }


            @Override
            public String hint() {
                // TODO: Pluralise when other orbs are added
                return "The orb is pretty, don't you just want to have it?";
            }
        });
    }


    private static Room createWizardBedroom() {
        final Room bedroom = new Room(
                "Bedroom", true,
                "A small bedroom no more than three meters square. Behind you is a bed with the thin, worn blanket "
                        + "rustled from a restless night. At its foot is a bookcase with a few clothes and two books "
                        + "you haven't read in years. To your left is a door slightly ajar, through it you can hear a"
                        + " lively conversation. In front of you in one corner is a fireplace with a small fire "
                        + "burning within it. Next to the fire a small wooden stool sits on its side, lying just shy "
                        + "of the flame's reach. Within the fire is an old book, so old that the pages have all "
                        + "fallen out and are scattered amongst the flames. One page managed to avoid the flame "
                        + "entirely and drifts towards your face, landing gently in front of you.",
                "A small bedroom with a bed, bookcase, stool and fire."
        );

        // Spell pages
        final String touchText = "Yep, feels just like paper.";
        final String dropText = "That's a bad idea but hell, what do I know?";
        final String takeText = "You pick up the page.";
        bedroom.addRoomObject(
                new TakableItem("Prayer to Talos", new String[]{"Talos", "Paper", "Page", "Talos page"}, touchText,
                                takeText,
                                dropText,
                                "'Wind wave' is written at the top of the page in ancient runes. Below is a calling "
                                        + "to Talos, god of storms, asking for his breath.",
                                false
                ));
        bedroom.addRoomObject(
                new TakableItem("Prayer to Helm", new String[]{"Helm", "helm page"}, touchText, takeText, dropText,
                                "A calling to Helm, god of protection, asking for his protection.", false
                ));
        bedroom.addRoomObject(
                new TakableItem("Prayer to Ralishaz", new String[]{"Ralishaz", "ralishaz page"}, touchText, takeText,
                                dropText,
                                "A calling to Ralishaz, god of ill luck and insanity, asking for his fortune to shine "
                                        + "upon those around you.",
                                false
                ));
        bedroom.addRoomObject(
                new TakableItem("Prayer to Belenus", new String[]{"Belenus", "belenus page"}, touchText, takeText,
                                dropText,
                                "A calling to Belenus, god of sun, light, and warmth, asking for his "
                                        + "radiance in this dark place.",
                                false
                ));
        bedroom.addRoomObject(
                new TakableItem("Prayer to Bast", new String[]{"Bast", "bast page"}, touchText, takeText, dropText,
                                "A calling to Bast, goddess of cats and vengeance, asking for her "
                                        + "strength in the task you have before you.",
                                false
                ));
        bedroom.addRoomObject(
                new TakableItem("Prayer to Balinor", new String[]{"Balinor", "balinor page"}, touchText, takeText,
                                dropText,
                                "A calling to Balinor, god of beasts and the hunt, asking for a friend.",
                                false
                ));

        // Furniture
        final RoomObject fire = new RoomObject(
                "Fire", new String[]{"Pages"}, "It's hot, your finger stings.", generateFireExamineText(bedroom),
                false
        );
        bedroom.addRoomObject(fire);
        bedroom.addRoomObject(new RoomObject("Bookshelf", new String[]{"Bookcase"},
                                             "You get some dust on your fingers. Maybe you should do a little spring "
                                                     + "cleaning.",
                                             "On this old set of shelves are all the possessions you have: two "
                                                     + "shirts, two pairs of trousers, a book of *cough* adult nature"
                                                     + " hidden below a book titled 'Wizarding Runes for Dummies', "
                                                     + "and a piece of stale bread - you meant to eat it last night "
                                                     + "but your forgot.",
                                             false
        ));
        bedroom.addRoomObject(new RoomObject("Bed", new String[]{},
                                             "It screams... just kidding it's a bed, and not a very soft one at that.",
                                             "Rustled from a poor night's sleep. You're sure you had a pillow but it "
                                                     + "seems to have gone missing since you went to bed last night",
                                             false
        ));
        bedroom.addRoomObject(new TakableItem("Robes, lifted", new String[]{"Robes lifted", "adult book"},
                                              "As you caress it you're reminded of the glorious sights within. You've"
                                                      + " read it so many times that it's burned into your memory.",
                                              "Best keep it on you in case someone comes snooping around. You don't "
                                                      + "want to be caught with such a thing, you'd never find a "
                                                      + "spouse.",
                                              "You're just going to leave it there and walk away???",
                                              "A book of female wizards lifting their robes to show their ankles. At "
                                                      + "the back there's a bonus story written by one of the most "
                                                      + "famous writers for the Lewd Wizards magazine."
        ));
        bedroom.addRoomObject(
                new TakableItem("Wizarding Runes for Dummies", new String[]{"Wizarding runes book", "Wizarding runes"},
                                "You creased a page.",
                                "It's good as reference. Not as good as the Encyclopedia Britannica, but it's much "
                                        + "more portable and usually does the job.",
                                "Hardbacks cost an arm and a leg.",
                                "The very book that inspired you to become a wizard. You've memorised all its "
                                        + "knowledge, but can't seem to let it go."
                ));
        bedroom.addRoomObject(new TakableItem("Shirt", new String[]{"tshirt", "t-shirt", "top"},
                                              "It's a shirt, you know what those feel like.",
                                              "I suppose it's useful in case you spill some food on this one.",
                                              "Oh, you paid good money for that...", "Just a plain white shirt."
        ));
        bedroom.addRoomObject(new TakableItem("Trousers", new String[]{"pair of trousers", "pants"},
                                              "It's a pair of trousers, you know that those feel like.",
                                              "Your mother always told you it's good to keep a spare pair on you when"
                                                      + " you leave the house in case you wet yourself.",
                                              "I mean, they weren't that expensive, but they have sentimental value...",
                                              ""
        ));
        bedroom.addRoomObject(
                new TakableItem("Stale bread", new String[]{"bread"}, "It's as hard as a rock. Not very apetising.",
                                "I suppose you can throw it at someone and kill them.", "Probably for the best.",
                                "By your estimates, it's about a week beyond the eat-by date."
                ));


        // First page taken
        GameLoop.addTrigger(new GameLoop.BlockingTrigger() {
            @Override
            public boolean acceptableAction(ParsedCommand parsedCommand) {
                return parsedCommand.getCommand() == Command.TAKE || (parsedCommand.getCommand() == Command.EXAMINE
                        && parsedCommand.getArguments().equals("fire"));
            }


            @Override
            public String blockingText() {
                return "You probably want to do something about the things in the fire first, they look important.";
            }


            @Override
            public boolean condition() {
                final Room room = Player.getPlayerLocation();

                // Check that the player took the original page
                if (room.contains("Talos")) {
                    return room.roomObjectsSize() == 13;
                }
                else {
                    return room.roomObjectsSize() == 12;
                }
            }


            @Override
            public void action() {
                fire.setExamineText(generateFireExamineText(bedroom));
                System.out.println(
                        "The ashes of the pages that were in the centre of the fire begin to crumble as the outer "
                                + "pages begin to blacken.");
            }


            @Override
            public String hint() {
                return "Might want to take a closer look at the fire.";
            }
        });

        // Second page taken
        GameLoop.addTrigger(new GameLoop.BlockingTrigger() {
            @Override
            public boolean acceptableAction(ParsedCommand parsedCommand) {
                return parsedCommand.getCommand() == Command.TAKE || parsedCommand.getCommand() == Command.EXAMINE;
            }


            @Override
            public String blockingText() {
                return "You probably want to do something about the things in the fire first, they look important.";
            }


            @Override
            public boolean condition() {
                final Room room = Player.getPlayerLocation();

                // Check that the player took the original page
                if (room.contains("Talos")) {
                    return room.roomObjectsSize() == 12;
                }
                else {
                    return room.roomObjectsSize() == 11;
                }
            }


            @Override
            public void action() {
                fire.setExamineText(generateFireExamineText(bedroom));
                System.out.println(
                        "As you grab the barely legible page and gently pat out the flames at its corner, the "
                                + "remaining pages become completely blackened and are engulfed by the flames.");

                // Delete all remaining pages
                for (String page : Arrays.asList("Helm", "Ralishaz", "Belenus", "Bast", "Balinor")) {
                    try {
                        bedroom.removeRoomObject(page);
                    } catch (IllegalArgumentException ignore) {
                    }
                }

                Player.getPlayerLocation().setExitsLocked(false);
            }


            @Override
            public String hint() {
                return "You only wanted one page from the fire? Be a bit more greedy - anything that's not nailed "
                        + "down can be yours.";
            }
        });

        return bedroom;
    }


    private static String generateFireExamineText(Room bedroom) {
        final List<String> godsOld = Arrays.asList(
                "Helm, god of protection", "Ralishaz, god of ill luck and insanity",
                "Belenus, god of sun, light, and warmth", "Bast, goddess of cats and vengeance",
                "Balinor, god of beasts and the hunt"
        );
        final List<String> gods = new ArrayList<>();
        for (String god : godsOld) {
            if (bedroom.contains(god.split(",")[0])) {
                gods.add(god);
            }
        }

        final StringBuilder godsString = new StringBuilder();
        for (int i = 0; i < gods.size(); i++) {
            if (i != gods.size() - 1) {
                godsString.append(gods.get(i));
                godsString.append(", ");
            }
            else {
                godsString.append("and ");
                godsString.append(gods.get(i));
            }
        }

        if (gods.size() == 5) {
            return "The pages that fell in the centre are already blackening in the flame's embrace, but the five "
                    + "pages "
                    + "that fell towards the edges are taking slower. At a glance you see that the pages within reach"
                    + " are devoted to "
                    + godsString.toString() + ". You reckon you can save two pages if you're quick.";
        }
        else if (gods.size() == 4) {
            return "The pages that fell in the centre are burnt beyond all hope, but the four pages that fell towards"
                    + " the edges are still savable. At a glance you see that the pages within reach are devoted to "
                    + godsString.toString() + ". You reckon you can save one more page if you're quick.";
        }
        else {
            return "The fire dies down, all the pages gone.";
        }
    }


    private static Room createOtherRoomsAndObjects(Room bedroom) {
        // Other rooms
        final Room livingRoom = new Room(
                "Living Room", "You find yourself in a larger living room with two small couches "
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


        final Room street = new Room(
                "Street", "You're in a loud street. You see your friends weave through the crowd to "
                + "the east.", "");
        livingRoom.addBidirectionalExit(bedroom, Direction.SOUTH);
        livingRoom.addBidirectionalExit(street, Direction.NORTH);

        final Room colosseum = new Room(
                "Colosseum", "You head through the tunnel and out into the arena of the colosseum. "
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
