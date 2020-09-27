import java.io.*;
import java.util.*;

/*
 * Program that completes a project described in a formated .txt file.
 *
 * Constructor accepts one (1) string value as an argument containing the .txt file name.
 *
 * Contains a private class Task,
 * which represents tasks in a project, or vertices in a digraph.
 * Task contains identifying and scheduling attributes, the number of dependencies in digraph
 * and an arraylist containing destination task of outgoing edges.
 */

public class CompleteProject {

  //Project name
  String projectName;
  //Array containing all tasks to be completed in project
  Task[] tasks;

  //Accepts name of file as argument - considering case file not found
  public CompleteProject(String inputFileName) throws FileNotFoundException {
    //Assign project name
    projectName = inputFileName;
    if(projectName.contains(".txt")) projectName = projectName.substring(0, projectName.length() - 4);
    //Different stages of project preparation and completion - easier to test processes seperately
    setup(new File(inputFileName));
    schedule();
    execute();
    printInfo();
  }

  //Task - private class representing each task in project/vertex in digraph
  //contains all attributes in the suggestes implementation
  private class Task {
    int id;
    String name;
    int time;
    int manpower;
    int earliestStart; int latestStart;
    ArrayList<Task> outEdges;
    int cntPredecessors;

    public Task(int i, String n, int t, int m) {
      id = i;
      name = n;
      time = t;
      manpower = m;
      outEdges = new ArrayList<Task>();
      cntPredecessors = 0;
      earliestStart = 0; latestStart = -1;
    }
  }

  //PROJECT STAGES - METHODS IN CONSTRUCTOR

  //setup - reads file, creates instances of tasks and assigns outEdges and cntPredecessors
  private void setup(File inputFile) throws FileNotFoundException {
    Scanner in = new Scanner(inputFile);
    //newline??
    tasks = new Task[in.nextInt()];
    ArrayList<int[]> edges = new ArrayList<int[]>();
    for(int i = 0; i < tasks.length; i++) {
      tasks[i] = new Task(in.nextInt(), in.next(), in.nextInt(), in.nextInt());
      int edgeOrigin = in.nextInt();
      while(edgeOrigin != 0) {
        int[] pair = {edgeOrigin, i + 1};
        edges.add(pair);
        edgeOrigin = in.nextInt();
      }
    }
    for(int[] edge : edges) {
      vertex(edge[0]).outEdges.add(vertex(edge[1]));
      vertex(edge[1]).cntPredecessors++;
    }
  }

  //schedule - terminates program if digraph is cyclic, assigns earliestStart, latestStart and slack
  private void schedule() {
    terminateIfCyclic();
    //Calculate earliest and latest start time
    ESS();
    LSS();
  }

  //execute - prints the execution schedule of project per time unit
  private void execute() {
    //Determine finish time
    int finishTime = 0;
    for(Task t : tasks) {
        if(t.outEdges.size() == 0 && ((t.earliestStart + t.time) > finishTime)) {
          finishTime = (t.earliestStart + t.time);
        }
    }

    System.out.println("\nProject: " + projectName + "\n");

    //current active tasks (for calculating current staff)
    ArrayList<Task> activeTasks = new ArrayList<Task>();

    String output = "";
    String started = ""; String finished = "";

    //execution loop - per time unit
    for(int time = 0; time <= finishTime; time++) {
        output += "Time: " + Integer.toString(time) + "\n";

        //determine starting and finishing tasks
        for(Task t : tasks) {
          if(time == t.latestStart) {
              started += "           Starting: " + Integer.toString(t.id) + "\n";
              activeTasks.add(t);
          } if(time == (t.latestStart + t.time)) {
              finished += "           Finished: " + Integer.toString(t.id) + "\n";
              activeTasks.remove(t);
          }
        }

        //determine if any changes - if not, don't bother printing
        if(started.equals("") && finished.equals("")) output = "";
        else output += finished + started;
        started = ""; finished = "";

        //if not on last time unit or no changes, don't bother with calculating manpower
        if(time != finishTime && !output.equals("")) {
            int currentManpower = 0;
            for(Task u : activeTasks) {
                currentManpower += u.manpower;
            } output += "      Current staff: " + Integer.toString(currentManpower) + "\n";
        } if(!output.equals("")) System.out.println(output);
        output = "";
    } System.out.println("**** Shortest possible project execution is " + finishTime + " ****");
  }

  //printInfo - prints info about the attributes of each task
  private void printInfo() {
    System.out.println("\nInfo:\n");

    //Explains output format
    System.out.println("Format");
    System.out.println("(id): (name)");
    System.out.println("t(ime):    m(anpower):");
    System.out.println("e(arliest start):    l(atest start):   s(lack):");
    System.out.println("Out(going edges - task dependent upon this task):");
    System.out.println("Pre(decessors - number thereof):");

    for(Task t : tasks) {
      System.out.println();
      System.out.println(t.id + ": " + t.name);
      System.out.println("t: " + t.time + " m: " + t.manpower);
      System.out.println("e: " + t.earliestStart + " l: " + t.latestStart + " s: " + (t.latestStart - t.earliestStart));
      System.out.print("Out: ");
      for(Task u : t.outEdges) {
        System.out.print(u.id + " ");
      } System.out.println();
      System.out.println("Pre: " + t.cntPredecessors);
    }
  }


  //TERMINATION CONDITIONS

  //terminateIfCyclic - terminates project if cycle in digraph
  private void terminateIfCyclic() {
    //Run recursive method for each independent task - explores each possible path
    for(Task t : independentVertices()) {
        terminateIfCyclic(t, new ArrayList<Task>());
    }
  } private void terminateIfCyclic(Task t, ArrayList<Task> path) {
      ArrayList<Task> thisPath = new ArrayList<Task>(path);
      //if contains loop, terminate program
      if(path.contains(t)) {
          System.out.println("Project is not realisable. Contains loop:");
          for(int i = path.indexOf(t); i < path.size(); i++) {
              System.out.print(vertex(i).id + ", ");
          } System.out.println(t.id);
          System.exit(0);
      }
      //else, recursive to next node in outEdges
      thisPath.add(t);
      for(Task u : t.outEdges) {
        terminateIfCyclic(u, thisPath);
      }
  }

  //SCHEDULING

  //ESS - calculates and assigns earliest start schedule
  private void ESS() {
    //Runs recursively for each independent task
    for(Task t : independentVertices()) {
        ESS(t);
    }
  } private void ESS(Task t) {
      if(t.cntPredecessors == 0) t.earliestStart = 0;
      //base case - if end task, return
      if(t.outEdges.size() == 0) return;
      //else - when most time consuming path up until this task has finished = earliestStart
      for(Task u : t.outEdges) {
          if(u.cntPredecessors == 1) u.earliestStart = (t.earliestStart + t.time);
          else {
              int earliestPathTime = (t.earliestStart + t.time);
              for(Task v : tasks) {
                  if(v.outEdges.contains(u)) {
                      if((v.earliestStart + v.time) > earliestPathTime) earliestPathTime = (v.earliestStart + v.time);
                  }
              } u.earliestStart = earliestPathTime;
          }
          //recursive step - next outEdge
          ESS(u);
      }
  }

  private void LSS() {
    ArrayList<Task> criticalPath = criticalPath();
    for(Task t : criticalPath) {
      //assign times for critical tasks
      t.latestStart= t.earliestStart;
    }
    for(Task t : tasks) {
      if(t.outEdges.size() == 0) LSS(t, criticalPath);
    }
  } private void LSS(Task t, ArrayList<Task> criticalPath) {
    //if no latestStart assigned...
    if(t.latestStart == -1) {
      //assign for non-critical end tasks
      if(t.outEdges.size() == 0) t.latestStart = criticalPath.get(0).latestStart + criticalPath.get(0).time - t.time;
      //assign others based on children
      else {
        int latestPossibleStart = 0;
        for(Task u : t.outEdges) {
          if(latestPossibleStart > -1) {
            if(u.latestStart > latestPossibleStart) latestPossibleStart = u.latestStart;
            if(u.latestStart == -1) latestPossibleStart = -1;
          }
        } if(latestPossibleStart != -1) t.latestStart = latestPossibleStart - t.time;
      }
    }

    //Recur predecessors
    for(Task u : tasks) {
      if(u.outEdges.contains(t)) LSS(u, criticalPath);
    }
  }


  //criticalPath - DEPENDENT UPON ESS - finds critical tasks in digraph
  //returns arraylist contains these tasks
  private ArrayList<Task> criticalPath() {
      ArrayList<Task> criticals = new ArrayList<Task>();

      //Identifies latest finishing end task and adds to criticals
      Task end = null;
      for(Task t : tasks) {
          if(t.outEdges.size() == 0) {
              if(end == null) end = t;
              else if(end.earliestStart < t.earliestStart) end = t;
          }
      }
      criticals.add(end);
      Task currentCritical = end;

      //Checks backwards for most time consuming path up until current critical task
      //adds identified critical tasks to critcals and returns when reaching an independent task
      while(currentCritical.cntPredecessors > 0) {
          ArrayList<Task> predecessors = new ArrayList<Task>();
          for(Task t : tasks) {
              if(t.outEdges.contains(currentCritical)) predecessors.add(t);
          } Task criticalPredecessor = predecessors.get(0);
          int criticalTime = (predecessors.get(0).earliestStart + predecessors.get(0).time);
          for(Task t : predecessors) {
              if(criticalTime < (t.earliestStart + t.time)) {
                  criticalTime = (t.earliestStart + t.time);
                  criticalPredecessor = t;
              }
          } currentCritical = criticalPredecessor;
          criticals.add(currentCritical);
      } return criticals;
  }

  //GRAPH METHODS

  //independentVertices - finds the independent tasks in digraph, where cntPredecessors == 0
  //returns array of these independent vertices
  private Task[] independentVertices() {
    ArrayList<Task> independents = new ArrayList<Task>();
    for(Task t : tasks) {
      if(t != null) {
        if(t.cntPredecessors == 0) independents.add(t);
      }
    } return independents.toArray(new Task[independents.size()]);
  }

  //Returns task assosiated with an id (id starts at 1)
  private Task vertex(int i) {return tasks[i - 1];}
}
