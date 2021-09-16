package muck.client.enduring_fantasy;

public class Magic {
    private Player pChar;
    private int mPcMp;
    private int mDmg;
    private int mCost;
    private boolean confirmMag;
    private boolean confirmDmg;

    public Magic(Player newPlay, int playMp){
        this.pChar = newPlay;
        this.mPcMp = playMp;
        this.mDmg = 0;
        this.confirmMag;
        this.confirmDmg;
    }

    public void magicMenu() {
        System.out.println("*-----* Magic *-----*\nPlease select what magic you want to use: \nUse Igni - Cost 5 MP - Fire" +
                "\nUse Ici - Cost 5 MP - Ice\nUse Levin - Cost 5 MP - Lightning\nUse Lumis - Cost 20 MP - Light \nUse Scathe - Cost 20 MP - Dark\nExit - exit");
    }

    public void confirmMagic() {
        if (this.mPcMp >= this.mCost) {
            this.confirmMag = true;
            this.mPcMp -= this.mCost;
        } else {
            this.confirmMag = false;
            System.out.println(this.pChar.getName() + " does not enough MP");
        }

    }

    public boolean getConfirmMag() {
        return this.confirmMag;
    }

    public boolean getConfirmDmg() {
        return this.confirmDmg;
    }

    public int getPlayerMP() {
        return this.mPcMp;
    }

    public int getMpCost() {
        return this.mCost;
    }

    public int getMagicDmg() {
        return this.mDmg;
    }

    public void useMagic(String magicName) {
        if (!magicName.equalsIgnoreCase("Igni") && !magicName.equalsIgnoreCase("Levin") && !magicName.equalsIgnoreCase("Ici")) {
            if (magicName.equalsIgnoreCase("Lumis")) {
                this.useLumis();
            } else if (magicName.equalsIgnoreCase("Scathe")) {
                this.useScathe();
            }
        } else {
            this.useElemental(magicName);
        }

    }

    public void useElemental(String magicName) {
        this.confirmMagic();
        if (this.getConfirmMag()) {
            this.mCost = 5;
            this.mDmg = this.pChar.getMagicStr() * 2;
            if (magicName.equalsIgnoreCase("Igni")) {
                System.out.println(this.pChar.getName() + " scorches the monster");
            } else if (magicName.equalsIgnoreCase("Ici")) {
                System.out.println(this.pChar.getName() + " freezes the monster");
            } else {
                System.out.println(this.pChar.getName() + " stings the monster");
            }
            this.confirmDmg = true;
        }
    }

    public void useLumis() {
        this.confirmMagic();
        this.mCost = 20;
        if (this.getConfirmMag()) {
            System.out.println(this.pChar.getName() + " sears the monster in prismatic light");
            this.mDmg = this.pChar.getMagicStr() * 5;
            this.confirmDmg = true;
        }
    }

    public void useScathe() {
        this.confirmMagic();
        if (this.getConfirmMag()) {
            System.out.println(this.pChar.getName() + " wracks the monster in umbral pain ");
            this.mDmg = this.pChar.getMagicStr() * 5;
            this.confirmDmg = true;
        }
    }
}