import java.util.Map;
import java.util.HashMap;

public class BooksCatalog {
    public static class Entry {
        public final String title;
        public final String author;
        public final String price;
        public final String imagePath;

        public Entry(String title, String author, String price, String imagePath) {
            this.title = title;
            this.author = author;
            this.price = price;
            this.imagePath = imagePath;
        }
    }

    private static final Map<String, Entry> MAP = new HashMap<>();

    static {
        // Physics
        MAP.put("Concepts In Thermal Physics", new Entry("Concepts In Thermal Physics", "by Jhon Ilpoulous", "500 DA", "BooksImg\\ImgPhysics/ConceptsInThermalPhysics.jpg"));
        MAP.put("Creative Physics Problems with Solutions", new Entry("Creative Physics Problems with Solutions", "by Jhon Ilpoulous", "1300 DA", "BooksImg\\ImgPhysics/CreativePhysicsProblemswithSolutions.jpg"));
        MAP.put("Nuclear And Particle Physics", new Entry("Nuclear And Particle Physics", "by Jhon Ilpoulous", "700 DA", "BooksImg\\ImgPhysics/NuclearAndParticlePhysics.jpg"));
        MAP.put("Origin Of Mass", new Entry("Origin Of Mass", "by Hamilton Education", "700 DA", "BooksImg\\ImgPhysics/OriginOfMass.jpg"));
        MAP.put("Princip Of Physics", new Entry("Princip Of Physics", "by Jhon Ilpoulous", "700 DA", "BooksImg\\ImgPhysics/PrincipOfPhysics.jpg"));
        MAP.put("Quantum Physics", new Entry("Quantum Physics", "by S.H.Lui", "1000 DA", "BooksImg\\ImgPhysics/QuantumPhysics.jpg"));

        // Math
        MAP.put("Pure Mathematics", new Entry("Pure Mathematics", "by Youssef N. Raffoul", "2200 DA", "BooksImg\\ImgMath/PureMathematics.jpg"));
        MAP.put("Mastering Algebra", new Entry("Mastering Algebra", "by Hamilton Education", "700 DA", "BooksImg\\ImgMath/MasteringAlgebra.jpg"));
        MAP.put("Numerical Analysis", new Entry("Numerical Analysis", "by S.H.Lui", "700 DA", "BooksImg\\ImgMath/NumericalAnalysis.jpg"));
        MAP.put("Applied Mathematics", new Entry("Applied Mathematics", "by J.David Logan", "700 DA", "BooksImg\\ImgMath/AppliedMathematics.jpg"));
        MAP.put("Advanced Defferential Equations", new Entry("Advanced Defferential Equations", "by Youssef N.Raffoul", "700 DA", "BooksImg\\ImgMath/AdvancedDefferentialEquations.jpg"));

        // Islamic
        MAP.put("Aquidat El Tawhid", new Entry("Aquidat El Tawhid", "Dr. Saleh El fawzan", "1200 DA", "BooksImg\\ImgIslamic/Aquidat_Tawhid.jpg"));
        MAP.put("Oumdat El Tafsir", new Entry("Oumdat El Tafsir", "Imam Ibn Kathir", "6700 DA", "BooksImg\\ImgIslamic/Oumdat_El_Tafsir.jpg"));
        MAP.put("El Rahique El Makhtoum", new Entry("El Rahique El Makhtoum", "Imam Moubarkfouri", "2500 DA", "BooksImg\\ImgIslamic/Rahique_Makhtoum.jpg"));
        MAP.put("Tadim El Salat", new Entry("Tadim El Salat", "Dr.Abd El Razeque El Badr", "1500 DA", "BooksImg\\ImgIslamic/Tadim_Salat.jpg"));
        MAP.put("Moulakhas El Feqhi", new Entry("Moulakhas El Feqhi", "Dr. Saleh El fawzan", "3000 DA", "BooksImg\\ImgIslamic/El_Moulakhas_El_Fequhi.jpg"));
        MAP.put("Sahih El Boukhari", new Entry("Sahih El Boukhari", "Imame Boukhari", "8000 DA", "BooksImg\\ImgIslamic/Boukhari.jpg"));

        // Programming / Algo
        MAP.put("Software Architecture", new Entry("Software Architecture", "Simon Tellier", "3300 DA", "BooksImg\\ImgAlgo/SoftwareArchitecture.jpg"));
        MAP.put("Algorithms Illuminated", new Entry("Algorithms Illuminated", "Tim Roughgarden", "700 DA", "BooksImg\\ImgAlgo/AlgorithmsIlluminated.jpg"));
        MAP.put("Data Structures", new Entry("Data Structures", "S.K.Srivastava", "1800 DA", "BooksImg\\ImgAlgo/DataStructures.jpg"));
        MAP.put("Introducing Algorithms in C", new Entry("Introducing Algorithms in C", "Luciano Manelli", "1800 DA", "BooksImg\\ImgAlgo/IntroductionAlgorithms.jpg"));
        MAP.put("Java For Beginners", new Entry("Java For Beginners", "Jack bin", "1800 DA", "BooksImg\\ImgAlgo/Java.jpg"));
        MAP.put("JavaScript Programming For Data Analysis", new Entry("JavaScript Programming For Data Analysis", "Charlie J.Barrett", "1800 DA", "BooksImg\\ImgAlgo/JavaScript.jpg"));

        // Chemistry
        MAP.put("Chemistry", new Entry("Chemistry", "by Gorge bill", "700 DA", "BooksImg\\ImgChemistry/Chemistry.jpg"));
        MAP.put("General Chemistry", new Entry("General Chemistry", "by Patric Moriss", "900 DA", "BooksImg\\ImgChemistry/GeneralChemistry.jpg"));
        MAP.put("Organic Chemistry", new Entry("Organic Chemistry", " by Tadashi Okuyama", "700 DA", "BooksImg\\ImgChemistry/OrganicChemistry.jpg"));

        // E-commerce / Misc
        MAP.put("E_commece Essentials", new Entry("E_commece Essentials", "by Jhon Ilpoulous", "600 DA", "BooksImg\\ImgE_commerce/E_commeceEssentials.jpg"));
        MAP.put("E_commerce Simplified", new Entry("E_commerce Simplified", " by Luis val", "1500 DA", "BooksImg\\ImgE_commerce/E_commerceSimplified.jpg"));

        // Other
        MAP.put("Fundamrntals Of Probability", new Entry("Fundamrntals Of Probability", " by Saeed Ghahramani", "1100 DA", "BooksImg\\ImgMath/FundamrntalsOfProbability.jpg"));
        MAP.put("Moulakhas El Feqhi", new Entry("Moulakhas El Feqhi", "Dr. Saleh El fawzan", "3000 DA", "BooksImg\\ImgIslamic/El_Moulakhas_El_Fequhi.jpg"));
    }

    public static String getPrice(String title) {
        Entry e = MAP.get(title);
        return e != null ? e.price : "0 DA";
    }

    public static String getAuthor(String title) {
        Entry e = MAP.get(title);
        return e != null ? e.author : "";
    }

    public static String getImagePath(String title) {
        Entry e = MAP.get(title);
        return e != null ? e.imagePath : "";
    }

    public static Entry get(String title) {
        return MAP.get(title);
    }
}
