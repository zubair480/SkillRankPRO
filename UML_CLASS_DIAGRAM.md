# UML Class Diagram - ATS Resume Ranking System

## Text-Based UML Class Diagram

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                                  Applicant                                   │
├─────────────────────────────────────────────────────────────────────────────┤
│ ATTRIBUTES (12 data points):                                                 │
│  - applicantId : String                                                     │
│  - name : String                                                            │
│  - email : String                                                           │
│  - resumeText : String                                                      │
│  - finalScore : double                                                      │
│  - extractedSkills : List<String>                                          │
│  - educationSection : String                                                │
│  - experienceSection : String                                               │
│  - projectsSection : String                                                 │
│  - certificationsSection : String                                           │
│  - totalExperienceYears : int                                              │
│  - formattingScore : int                                                    │
├─────────────────────────────────────────────────────────────────────────────┤
│ CONSTRUCTORS:                                                                │
│  + Applicant()                                                              │
│  + Applicant(String, String, String, String)                               │
├─────────────────────────────────────────────────────────────────────────────┤
│ METHODS (Getters & Setters for all 12 attributes):                          │
│  + getApplicantId() : String                                                │
│  + setApplicantId(String) : void                                            │
│  + getName() : String                                                       │
│  + setName(String) : void                                                   │
│  + getEmail() : String                                                      │
│  + setEmail(String) : void                                                  │
│  ... (+ 22 more getters/setters)                                            │
└─────────────────────────────────────────────────────────────────────────────┘
                                      △
                                      │
                                      │ extends
                                      │
┌─────────────────────────────────────────────────────────────────────────────┐
│                             ScoredApplicant                                  │
├─────────────────────────────────────────────────────────────────────────────┤
│ INHERITS FROM: Applicant (all 12 parent attributes)                         │
│                                                                              │
│ ADDITIONAL ATTRIBUTES (7 data points):                                      │
│  - keywordMatches : int                                                     │
│  - matchSummary : String                                                    │
│  - skillScore : double                                                      │
│  - keywordScore : double                                                    │
│  - experienceScore : double                                                 │
│  - formattingScorePart : double                                             │
│  - extrasScore : double                                                     │
├─────────────────────────────────────────────────────────────────────────────┤
│ CONSTRUCTORS:                                                                │
│  + ScoredApplicant()                                                        │
│  + ScoredApplicant(Applicant)                    {Copy constructor}         │
├─────────────────────────────────────────────────────────────────────────────┤
│ METHODS:                                                                     │
│  + getKeywordMatches() : int                                                │
│  + setKeywordMatches(int) : void                                            │
│  + getMatchSummary() : String                                               │
│  + setMatchSummary(String) : void                                           │
│  ... (+ 12 more getters/setters for scoring attributes)                     │
│                                                                              │
│ INHERITED METHODS from Applicant:                                           │
│  + All getters/setters from parent class                                    │
└─────────────────────────────────────────────────────────────────────────────┘


┌─────────────────────────────────────────────────────────────────────────────┐
│                               JobRole                                        │
├─────────────────────────────────────────────────────────────────────────────┤
│ ATTRIBUTES (3 data points):                                                  │
│  - roleName : String                                                        │
│  - skills : List<String>                                                    │
│  - keywords : List<String>                                                  │
├─────────────────────────────────────────────────────────────────────────────┤
│ CONSTRUCTORS:                                                                │
│  + JobRole(String, List<String>, List<String>)                              │
├─────────────────────────────────────────────────────────────────────────────┤
│ METHODS:                                                                     │
│  + getRoleName() : String                                                   │
│  + getSkills() : List<String>                                               │
│  + getKeywords() : List<String>                                             │
└─────────────────────────────────────────────────────────────────────────────┘


┌─────────────────────────────────────────────────────────────────────────────┐
│                            RankingEngine                                     │
├─────────────────────────────────────────────────────────────────────────────┤
│ ATTRIBUTES: None (stateless utility class)                                   │
├─────────────────────────────────────────────────────────────────────────────┤
│ USER-DEFINED METHODS (9 custom methods):                                    │
│                                                                              │
│ SCORING CALCULATIONS (5 components):                                        │
│  + calculateSkillScore(Applicant, JobRole) : double                         │
│      Calculates matching required skills (0-40 points)                      │
│                                                                              │
│  + calculateKeywordScore(Applicant, JobRole) : double                       │
│      Counts job keywords in resume (0-25 points)                            │
│                                                                              │
│  + calculateExperienceScore(Applicant) : double                             │
│      Evaluates years of experience (0-20 points)                            │
│                                                                              │
│  + calculateFormattingScore(Applicant) : double                             │
│      Assesses resume quality (0-10 points)                                  │
│                                                                              │
│  + calculateExtrasScore(Applicant) : double                                 │
│      Bonus for certs & projects (0-5 points)                               │
│                                                                              │
│ COMPOSITE METHODS:                                                           │
│  + calculateFinalScoreForRole(Applicant, JobRole) : double                  │
│      Combines all 5 scores into total (0-100)                               │
│                                                                              │
│  + scoreApplicantAcrossRoles(Applicant, List<JobRole>) : Map                │
│      Evaluates against multiple roles                                       │
│                                                                              │
│ ANALYSIS METHODS:                                                            │
│  + getBestRole(Map) : Map.Entry<String, Double>                             │
│      Finds role with highest score                                          │
│                                                                              │
│  + displayRoleRanking(Map) : void                                           │
│      Formats and displays results                                           │
└─────────────────────────────────────────────────────────────────────────────┘


┌─────────────────────────────────────────────────────────────────────────────┐
│                             FileManager                                      │
├─────────────────────────────────────────────────────────────────────────────┤
│ ATTRIBUTES: None (stateless file I/O utility)                               │
├─────────────────────────────────────────────────────────────────────────────┤
│ USER-DEFINED METHODS (2 custom methods):                                    │
│                                                                              │
│ FILE WRITE OPERATION:                                                        │
│  + saveScores(Applicant, Map<String,Double>, String) : void                │
│      Persists scoring results to results.txt                                │
│      Appends applicant scores and role rankings                             │
│                                                                              │
│ FILE READ OPERATION:                                                         │
│  + readScores() : List<String>                                              │
│      Retrieves previously saved scores from results.txt                     │
│      Returns all lines as list for display/processing                       │
│                                                                              │
│ FILE FORMAT:                                                                 │
│      Applicant: [ID] - [Name]                                               │
│      Email: [Email]                                                         │
│      Best Fit Role: [Role] (Score: [Score])                                 │
│      All Role Scores                                                        │
│      [Role] : [Score]                                                       │
│      ======================================                                  │
└─────────────────────────────────────────────────────────────────────────────┘


┌─────────────────────────────────────────────────────────────────────────────┐
│                             Main (Driver)                                    │
├─────────────────────────────────────────────────────────────────────────────┤
│ ATTRIBUTES: None                                                             │
├─────────────────────────────────────────────────────────────────────────────┤
│ METHODS:                                                                     │
│  + main(String[]) : void        {Application entry point}                   │
│      Orchestrates the entire ATS evaluation workflow:                       │
│      1. Gather applicant input (ID, name, email, resume path)               │
│      2. Parse resume PDF and extract text                                   │
│      3. Extract structured information (skills, experience, education)      │
│      4. Load job roles for evaluation                                       │
│      5. Score applicant against all roles                                   │
│      6. Display results and save to file                                    │
└─────────────────────────────────────────────────────────────────────────────┘
```

## Class Relationships Diagram

```
┌──────────────────────────────────────────────────────────────────┐
│                    APPLICATION FLOW                              │
└──────────────────────────────────────────────────────────────────┘

    Main
    │
    ├──> ResumeParser: extractText() ────────┐
    │                                         │
    ├──> SectionExtractor: extractSection()   │
    │                                         │
    ├──> ExperienceExtractor: extractYears()  │──> Applicant
    │                                         │    (created with
    ├──> SkillExtractor: extractSkills()      │     extracted data)
    │                                         │
    └──> FormattingScoreCalculator: ...───────┘
                                        
    Main
    │
    ├──> RoleManager: loadRoles() ────────────> List<JobRole>
    │                                          (e.g., Java Developer,
    │                                           Data Analyst,
    │                                           AI Engineer)
    │
    ├──> RankingEngine: scoreApplicantAcrossRoles(Applicant, roles)
    │         │
    │         └──> Scores Applicant against each JobRole
    │             Returns: Map<String, Double>
    │                     (Role Name -> Score)
    │
    ├──> RankingEngine: getBestRole() ────────> Best matching role
    │
    ├──> RankingEngine: displayRoleRanking() ──> Console output
    │
    └──> FileManager: saveScores() ──────────> results.txt
             │
             └──> File I/O: Persists results


┌──────────────────────────────────────────────────────────────────┐
│              INHERITANCE HIERARCHY                               │
└──────────────────────────────────────────────────────────────────┘

    Applicant (Parent Class)
    │
    ├── Attributes: applicantId, name, email, resumeText, finalScore
    │               extractedSkills, education, experience, projects
    │               certifications, totalExperienceYears, formattingScore
    │
    ├── Constructors: (no args), (4 args)
    │
    └── ScoredApplicant (Child Class)
        │
        └── Inherits all 12 attributes from Applicant
            Adds 7 scoring-specific attributes
            Constructors: (no args), (Applicant arg - copy constructor)
```

## Component Interaction Diagram

```
USER INPUT
    │
    ▼
┌──────────────────────────────────────┐
│  1. RESUME EXTRACTION PHASE          │
│  - Read PDF file                     │
│  - Extract text                      │
│  - Parse sections                    │
│  - Calculate formatting score        │
└──────────────────────────────────────┘
    │
    ▼
    ┌─────────────────────┐
    │    Applicant        │
    │   (with extracted   │
    │   resume data)      │
    └─────────────────────┘
    │
    ▼
┌──────────────────────────────────────┐
│  2. JOB ROLE LOADING PHASE           │
│  - Load job requirements             │
│  - Define required skills            │
│  - Define keywords                   │
└──────────────────────────────────────┘
    │
    ▼
    ┌──────────────────────────┐
    │   List<JobRole>          │
    │  (3+ job roles loaded)   │
    └──────────────────────────┘
    │
    ▼
┌──────────────────────────────────────┐
│  3. RANKING & SCORING PHASE          │
│  RankingEngine:                      │
│  - calculateSkillScore()             │
│  - calculateKeywordScore()           │
│  - calculateExperienceScore()        │
│  - calculateFormattingScore()        │
│  - calculateExtrasScore()            │
│  - generateFinalScores()             │
└──────────────────────────────────────┘
    │
    ▼
    ┌──────────────────────────────┐
    │  Map<String, Double>         │
    │  Scores for each role:       │
    │  "Java Developer": 73.86     │
    │  "Data Analyst": 37.67       │
    │  "AI Engineer": 31.0         │
    └──────────────────────────────┘
    │
    ▼
┌──────────────────────────────────────┐
│  4. RESULT DISPLAY & PERSISTENCE     │
│  - Display rankings                  │
│  - Show best fit role                │
│  - Save to file (FileManager)        │
└──────────────────────────────────────┘
    │
    ▼
CONSOLE OUTPUT + results.txt FILE
```

---

## Project Requirements Met ✓

| Requirement | Class(es) | Details |
|---|---|---|
| 3+ User Classes | 9 total | Applicant, ScoredApplicant, JobRole, RankingEngine, FileManager, + 4 more |
| **Inheritance** | **ScoredApplicant extends Applicant** | ✓ Demonstrated |
| **5+ Data Points** | **Applicant (12 attributes)** | ✓ Exceeded |
| **3+ Custom Methods** | **RankingEngine (9 methods)** | ✓ Exceeded |
| **2+ Constructors** | **Applicant & ScoredApplicant** | ✓ Both have 2 constructors |
| **File I/O** | **FileManager** | ✓ saveScores() & readScores() |
| **Detailed Comments** | **All classes** | ✓ Javadoc + inline comments |
| **UML Diagram** | **This document** | ✓ Provided |

