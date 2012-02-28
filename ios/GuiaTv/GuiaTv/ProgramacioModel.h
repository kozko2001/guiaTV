//
//  ProgramacioModel.h
//  GuiaTv
//
//  Created by Jordi Coscolla on 25/02/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "ProgramaModel.h"

@interface ProgramacioModel : NSObject
{
    NSArray     *sections;
    NSArray     *keys;
}
- (int) numDias;
- (int) numProgramasByDia: (int) dia;
- (ProgramaModel*) getProgramaByDia: (int) dia AndRow:(int) row;
- (ProgramacioModel*) initFromProgramas: (NSArray *) programas;
- (int) getSectionDate: (int) section;
@end
