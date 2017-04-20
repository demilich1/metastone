package net.demilich.metastone.gui.deckbuilder.importer;

public class ImporterFactory {

    public IDeckImporter createDeckImporter(String url)
    {
        if(url == null)
            return null;

        if(url.contains("hearthpwn.com"))
            return new HearthPwnImporter();

        if(url.contains("tempostorm.com"))
           return new TempostormImporter();
        
        if(url.contains("icy-veins.com"))
            return new IcyVeinsImporter();
        
        if(url.contains("hearthhead.com"))
            return new HearthHeadImporter();

        return null;
    }
}
