package Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nick on 14/04/2015.
 */
public enum Property {
    Taxonomy {
        @Override
        public PropertyType type() {
            return PropertyType.RESOURCE;
        }

        @Override
        public List<ProcessType> scope() {
            List<ProcessType> list = new ArrayList<>();
            list.add(ProcessType.XCT);
            list.add(ProcessType.XRT);
            list.add(ProcessType.XVT);
            return list;
        }
    },
    Input {
        @Override
        public PropertyType type() {
            return PropertyType.RESOURCE;
        }

        @Override
        public List<ProcessType> scope() {
            List<ProcessType> list = new ArrayList<>();
            list.add(ProcessType.XCT);
            list.add(ProcessType.XRT);
            list.add(ProcessType.XVT);
            return list;
        }
    },
    Output {
        @Override
        public PropertyType type() {
            return PropertyType.OUTPUT_RESOURCE;
        }

        @Override
        public List<ProcessType> scope() {
            List<ProcessType> list = new ArrayList<>();
            list.add(ProcessType.XCT);
            list.add(ProcessType.XRT);
            list.add(ProcessType.XVT);
            return list;
        }
    },
    TaxoMode {
        @Override
        public PropertyType type() {
            return PropertyType.STRING;
        }

        @Override
        public List<ProcessType> scope() {
            List<ProcessType> list = new ArrayList<>();
            list.add(ProcessType.XCT);
            list.add(ProcessType.XRT);
            return list;
        }
    },
    TaxoId {
        @Override
        public PropertyType type() {
            return PropertyType.STRING;
        }

        @Override
        public List<ProcessType> scope() {
            List<ProcessType> list = new ArrayList<>();
            list.add(ProcessType.XCT);
            list.add(ProcessType.XRT);
            return list;
        }
    },
    EntityMode {
        @Override
        public PropertyType type() {
            return PropertyType.STRING;
        }

        @Override
        public List<ProcessType> scope() {
            List<ProcessType> list = new ArrayList<>();
            list.add(ProcessType.XCT);
            list.add(ProcessType.XRT);
            return list;
        }
    },
    Entity {
        @Override
        public PropertyType type() {
            return PropertyType.STRING;
        }

        @Override
        public List<ProcessType> scope() {
            List<ProcessType> list = new ArrayList<>();
            list.add(ProcessType.XCT);
            return list;
        }
    },
    EntityScheme {
        @Override
        public PropertyType type() {
            return PropertyType.STRING;
        }

        @Override
        public List<ProcessType> scope() {
            List<ProcessType> list = new ArrayList<>();
            list.add(ProcessType.XCT);
            return list;
        }
    },
    DateMode {
        @Override
        public PropertyType type() {
            return PropertyType.STRING;
        }

        @Override
        public List<ProcessType> scope() {
            List<ProcessType> list = new ArrayList<>();
            list.add(ProcessType.XCT);
            list.add(ProcessType.XRT);
            return list;
        }
    },
    StartDate {
        @Override
        public PropertyType type() {
            return PropertyType.DATE;
        }

        @Override
        public List<ProcessType> scope() {
            List<ProcessType> list = new ArrayList<>();
            list.add(ProcessType.XCT);
            list.add(ProcessType.XRT);
            return list;
        }
    },
    EndDate {
        @Override
        public PropertyType type() {
            return PropertyType.DATE;
        }

        @Override
        public List<ProcessType> scope() {
            List<ProcessType> list = new ArrayList<>();
            list.add(ProcessType.XCT);
            list.add(ProcessType.XRT);
            return list;
        }
    },
    UnitMode {
        @Override
        public PropertyType type() {
            return PropertyType.STRING;
        }

        @Override
        public List<ProcessType> scope() {
            List<ProcessType> list = new ArrayList<>();
            list.add(ProcessType.XCT);
            return list;
        }
    },
    Unit {
        @Override
        public PropertyType type() {
            return PropertyType.STRING;
        }

        @Override
        public List<ProcessType> scope() {
            List<ProcessType> list = new ArrayList<>();
            list.add(ProcessType.XCT);
            return list;
        }
    },
    TableId {
        @Override
        public PropertyType type() {
            return PropertyType.STRING;
        }

        @Override
        public List<ProcessType> scope() {
            List<ProcessType> list = new ArrayList<>();
            list.add(ProcessType.XCT);
            return list;
        }
    },
    Validation {
        @Override
        public PropertyType type() {
            return PropertyType.BOOLEAN;
        }

        @Override
        public List<ProcessType> scope() {
            List<ProcessType> list = new ArrayList<>();
            list.add(ProcessType.XCT);
            list.add(ProcessType.XRT);
            return list;
        }
    },
    ValidationFolder {
        @Override
        public PropertyType type() {
            return null;
        }

        @Override
        public List<ProcessType> scope() {
            return null;
        }
    },
    XvtXpeConfig {
        @Override
        public PropertyType type() {
            return null;
        }

        @Override
        public List<ProcessType> scope() {
            return null;
        }
    },
    CustomMessages {
        @Override
        public PropertyType type() {
            return null;
        }

        @Override
        public List<ProcessType> scope() {
            return null;
        }
    },
    Suffix {
        @Override
        public PropertyType type() {
            return PropertyType.STRING;
        }

        @Override
        public List<ProcessType> scope() {
            List<ProcessType> list = new ArrayList<>();
            list.add(ProcessType.XCT);
            return list;
        }
    },
    SubsetConversion {
        @Override
        public PropertyType type() {
            return PropertyType.BOOLEAN;
        }

        @Override
        public List<ProcessType> scope() {
            List<ProcessType> list = new ArrayList<>();
            list.add(ProcessType.XCT);
            return list;
        }
    },
    UseCache {
        @Override
        public PropertyType type() {
            return null;
        }

        @Override
        public List<ProcessType> scope() {
            return null;
        }
    },
    CachePath {
        @Override
        public PropertyType type() {
            return null;
        }

        @Override
        public List<ProcessType> scope() {
            return null;
        }
    };

   public  abstract PropertyType type();
   public  abstract List<ProcessType> scope();
   public List<String> scopeStr() {
        List<String> scopeString = new ArrayList<>();
        for(ProcessType item : scope()){
            scopeString.add(item.name());
        }
       return scopeString;
   }
}
