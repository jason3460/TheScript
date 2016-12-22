// package scripts.TheScript.tasks.combat.chicken;
//
// import org.tribot.api.General;
// import org.tribot.api.Timing;
// import org.tribot.api2007.Banking;
// import org.tribot.api2007.Equipment;
// import org.tribot.api2007.Inventory;
//
// import scripts.TheScript.api.antiban.Antiban;
// import scripts.TheScript.api.conditions.Conditions;
// import scripts.TheScript.api.methods.Bank;
// import scripts.TheScript.api.methods.Gear;
// import scripts.TheScript.api.methods.Killing;
// import scripts.TheScript.api.methods.Methods;
// import scripts.TheScript.enums.Monster;
// import scripts.TheScript.variables.Variables;
//
// public class Chicken {
//
// public static void doChickens() {
//
// if (Killing.checkCombatStance(2)) {
// if (Methods.inArea(Monster.CHICKEN.getArea())) {
// Variables.miniState = "at chickens";
// if (Inventory.isFull()) {
// Variables.miniState = "walking to bank";
// Bank.walkToBank();
// } else {
// Variables.miniState = "Killing";
// Killing.combat(Monster.CHICKEN.getName(), Monster.CHICKEN.getArea(),
// Monster.CHICKEN.getLoot());
// }
// } else if (Bank.isInBank()) {
// Variables.miniState = "in bank";
// if (Inventory.isFull()) {
// Variables.miniState = "doing bank";
// handleBank();
// } else {
// Variables.miniState = "walking to chicken area";
// Methods.walkToTile(Monster.CHICKEN.getArea().getRandomTile());
// }
//
// } else {
// if (Inventory.isFull()) {
// Variables.miniState = "walking to bank";
// Bank.walkToBank();
// } else {
// Variables.miniState = "walking to chicken area";
// Methods.walkToTile(Monster.CHICKEN.getArea().getRandomTile());
// }
//
// }
// }
// }
//
// public static void handleChickens() {
// Variables.miniState = "chickens till 10";
//
// if (Variables.initialBank) {
// if (!Gear.isAllEquipped(Monster.CHICKEN.getGear())) {
// Gear.getTaskGear(Monster.CHICKEN.getGear());
// } else {
// Chicken.doChickens();
// }
// } else {
// doInitialBank();
// }
// }
//
// private static void doInitialBank() {
// Variables.miniState = "Initial Bank";
// if (Bank.isInBank()) {
// if (Inventory.getAll().length > 0) {
// Bank.depositAllInventory();
// } else if(Equipment.getItems().length > 0){
// Bank.depositAllEquipment();
// } else {
// Variables.initialBank = true;
// }
// } else {
// Bank.walkToBank();
// }
// }
//
// private static void handleBank() {
// if (Banking.isBankScreenOpen()) {
// Banking.depositAll();
// Timing.waitCondition(Conditions.get().inventoryEmpty(), General.random(4000,
// 6000));
// } else {
// if (Banking.openBank()) {
// Timing.waitCondition(Conditions.get().bankOpen(), General.random(4000,
// 6000));
// }
// }
// Antiban.generateTrackers(Antiban.getWaitingTime());
// }
//
// }
