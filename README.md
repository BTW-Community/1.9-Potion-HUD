# 1.9 Potion HUD Addon
This addon ports over the potion HUD from 1.9 Vanilla. ![Here's a preview of it working in BTW](https://imgur.com/f7NCovU.gif)

[Download](https://github.com/BTW-Community/1.9-Potion-HUD/releases/latest)


If you would like to use the 1.14 potion icons, you can also download the texture pack here:

[1.14 Potion HUD Textures](https://github.com/BTW-Community/1.9-Potion-HUD/releases/tag/textures)

Additionally this provides an option to disable Minecraft shifting the inventory when a potion effect is applied. You can change this functionality by editing the config located in `*/config/PHUD.properties`, and setting `potionPush` to one of three possible values:
- `true`
- `false`
- `auto` (default)

Setting `potionPush=true` is equivalent to Vanilla functionality. `potionPush=false` keeps the GUI in the center of the screen at all times, which may cause the status icons to go off screen for small resolutions. `potionPush=auto` attempts to keep the GUI at the center of the screen if there is enough space to do so, and otherwise reverts to Vanilla functionality.

## Compatibility
<details>
  <summary>Modified classes</summary>
  
  - BlockBrewingStand (Client)
  - GuiIngame (Client)
  - InventoryEffectRenderer (Client)
</details>
