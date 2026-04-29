package models;

import java.util.List;

public class Trajectory {




        private String vertrek;
        private String aankomst;
        private List<String> tijden;

        public Trajectory(String vertrek, String aankomst, List<String> tijden) {
            this.vertrek = vertrek;
            this.aankomst = aankomst;
            this.tijden = tijden;
        }

        public String getVertrek() { return vertrek; }
        public String getAankomst() { return aankomst; }
        public List<String> getTijden() { return tijden; }

        public String toString() {
            return vertrek + " → " + aankomst;
        }
    }


