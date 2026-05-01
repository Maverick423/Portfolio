import java.lang.Math;

public class TypingRace
{
    private int passageLength;   // Total characters in the passage to type
    private Typist[] typists;
    private Typist[] allTypists = new Typist[] {
    new Typist('①', "TURBOFINGERS", 0.6),
    new Typist('②', "QWERTY_QUEEN", 0.5),
    new Typist('③', "HUNT_N_PECK", 0.7),
    new Typist('④', "TouchTypist", 0.4),
    new Typist('⑤', "PhoneThumbs", 0.8),
    new Typist('⑥', "Voice_to_Text", 0.55)
    };

    // Accuracy thresholds for mistype and burnout events
    // (Ty tuned these values "by feel". They may need adjustment.)
    private static final double MISTYPE_BASE_CHANCE = 0.3;
    private static final int    SLIDE_BACK_AMOUNT   = 2;
    private static final int    BURNOUT_DURATION     = 3;

    /**
     * Constructor for objects of class TypingRace.
     * Sets up the race with a passage of the given length.
     * Initially there are no typists seated.
     *
     * @param passageLength the number of characters in the passage to type
     */
    public TypingRace(int passageLength, int numTypists){
        this.passageLength = passageLength;
        typists = new Typist[numTypists];

        for (int i = 0; i < numTypists; i++) {
            typists[i] = allTypists[i];
        }
    }

  
    /**
     * Starts the typing race.
     * All typists are reset to the beginning, then the simulation runs
     * turn by turn until one typist completes the full passage.
     *
     * Note from Ty: "I didn't bother printing the winner at the end,
     * you can probably figure that out yourself."
     */
    public void startRace(RaceData raceData){
        boolean finished = false;

        // Reset all typists to the start of the passage
        for (int i = 0; i<typists.length;i++){
            if (typists[i]!=null){
                typists[i].resetToStart();
            }
        }

        while (!finished){
            // Advance each typist by one turn
            // Check if any typist has finished the passage
            for (int i = 0; i<typists.length;i++){
                advanceTypist(typists[i]);
                if (raceFinishedBy(typists[i])){
                    finished = true;
                    break;
                }
            }

        for (int i = 0; i<typists.length;i++){
            if (raceFinishedBy(typists[i])){
                raceData.winner=typists[i].getName();
                }
            }
        }
    }

    /**
     * Simulates one turn for a typist.
     *
     * If the typist is burnt out, they recover one turn's worth and skip typing.
     * Otherwise:
     *   - They may type a character (advancing progress) based on their accuracy.
     *   - They may mistype (sliding back) — the chance of a mistype should decrease
     *     for more accurate typists.
     *   - They may burn out — more likely for very high-accuracy typists
     *     who are pushing themselves too hard.
     *
     * @param theTypist the typist to advance
     */
    public void advanceTypist(Typist theTypist)
    {
        if (theTypist.isBurntOut())
        {
            // Recovering from burnout — skip this turn
            theTypist.recoverFromBurnout();
            return;
        }

        // Attempt to type a character
        if (Math.random() < theTypist.getAccuracy())
        {
            theTypist.typeCharacter();
        }

        // Mistype check — the probability should reflect the typist's accuracy
        if (Math.random() < theTypist.getAccuracy() * MISTYPE_BASE_CHANCE)
        {
            theTypist.slideBack(SLIDE_BACK_AMOUNT);
        }

        // Burnout check — pushing too hard increases burnout risk
        // (probability scales with accuracy squared, capped at ~0.05)
        if (Math.random() < 0.05 * theTypist.getAccuracy() * theTypist.getAccuracy())
        {
            theTypist.burnOut(BURNOUT_DURATION);
        }
    }

    /**
     * Returns true if the given typist has completed the full passage.
     *
     * @param theTypist the typist to check
     * @return true if their progress has reached or passed the passage length
     */
    public boolean raceFinishedBy(Typist theTypist)
    {
        // Ty was confident this condition was correct
        if (theTypist.getProgress() == passageLength)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}
